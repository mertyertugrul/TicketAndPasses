package com.mertugrul.LeisurePass.service;

import com.mertugrul.LeisurePass.exception.ApiRequestException;
import com.mertugrul.LeisurePass.model.PassActivationRespond;
import com.mertugrul.LeisurePass.model.PassDatasource;
import com.mertugrul.LeisurePass.model.UpdatePassRequest;
import com.mertugrul.LeisurePass.model.VerificationRespond;
import com.mertugrul.LeisurePass.model.entity.Customer;
import com.mertugrul.LeisurePass.model.entity.Pass;
import com.mertugrul.LeisurePass.model.entity.Vendor;
import com.mertugrul.LeisurePass.repo.CustomerRepository;
import com.mertugrul.LeisurePass.repo.PassRepository;
import com.mertugrul.LeisurePass.repo.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * Service class for Leisure Pass Challenge Business Logic.
 */
@Service
public class PassService {

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VendorRepository vendorRepository;

    /**
     * Create Pass for addPass request.
     * PassId is universally unique identifier (UUID), so every PassId created would be unique and probability of
     * collusion is almost 0.
     * Will throw exception if CustomerId or VendorId is not exits.
     *
     * @param createPass
     * @return
     * @throws ApiRequestException
     */
    public Pass createPass(PassDatasource createPass){

        // Getting customer
        Customer customerData = customerRepository.findById(Long.parseLong(createPass.getCustomerId())).orElseThrow(
                () -> {
                    throw new ApiRequestException("There is no customer by customer id: " + createPass.getCustomerId());
                }
        );

        // Getting vendor
        Vendor vendorData = vendorRepository.findById(Long.parseLong(createPass.getVendorId())).orElseThrow(
                () -> {
                    throw new ApiRequestException("There is no vendor by vendor id: " + createPass.getVendorId());
                }
        );


        // Persist pass
        Pass newPass = new Pass();

        // Pass information setting
        newPass.setId(UUID.randomUUID().toString());
        newPass.setCityName(createPass.getCity());
        newPass.setActivationDate(Instant.now());
        newPass.setPassLength(createPass.getPassLength());
        newPass.setExpireDate(Instant.now().plus(createPass.getPassLength(), ChronoUnit.HOURS));

        // Costumer information setting
        newPass.setCustomerId(customerData.getCustomerId());
        newPass.setCustomerName(customerData.getFullName());
        newPass.setCustomerAddress(customerData.getAddress());

        // Vendor information setting
        newPass.setVendorId(vendorData.getVendorId());
        newPass.setVendorName(vendorData.getVendorName());
        newPass.setVendorAddress(vendorData.getAddress());

        return passRepository.save(newPass);
    }

    /**
     * List every Pass created from database.
     * @return
     */
    public List<Pass> getAllPasses(){
        return (List<Pass>) passRepository.findAll();
    }

    /**
     * Retrieves Pass from database by ID.
     * Throws exception if ID is invalid.
     *
     * @param id
     * @return
     * @throws ApiRequestException
     */
    public Pass getPassById(String id){

        return passRepository.findById(id).orElseThrow(() ->{
            throw new ApiRequestException("Pass couldn't found for id: " + id);
        });

    }

    /**
     * Retrieves Pass from database by ID and throws exception if ID is invalid.
     * Timestamp and PassLength will determine if the Pass is still active.
     * For client response PassActivationRespond object is created.
     *
     * @param id
     * @return
     * @throws ApiRequestException
     */
    public PassActivationRespond checkPassActive(String id){

        Pass pass = passRepository.findById(id).orElseThrow(() ->{
            throw new ApiRequestException("Pass couldn't found for id: " + id);
        });

        PassActivationRespond passActivationRespond = new PassActivationRespond();
        passActivationRespond.setPassId(pass.getId());
        passActivationRespond.setIsActive(pass.getActivationDate().plus(pass.getPassLength(),
                ChronoUnit.HOURS).isAfter(Instant.now()));

        return passActivationRespond;

    }

    /**
     * Retrieves Pass from database by ID and throws exception if ID is invalid.
     * Determine if the passed VendorId is the same with on the Pass.
     * For client response PassActivationRespond object is created.
     * @param passId
     * @param vendorId
     * @return
     * @throws ApiRequestException
     */
    public VerificationRespond verifyPass(String passId, Long vendorId){


        Pass pass = passRepository.findById(passId).orElseThrow(() ->{
            throw new ApiRequestException("Pass couldn't found for id: " + passId);
        });

        if (pass.getVendorId().equals(vendorId)){
            VerificationRespond verificationRespond = new VerificationRespond();
            verificationRespond.setVendorId(vendorId);
            verificationRespond.setPassId(passId);
            verificationRespond.setIsValid(true);
            return verificationRespond;
        } else {
            throw new ApiRequestException("Vendor ID: " + vendorId + " is not valid for pass id: " + passId);
        }
    }

    /**
     * Retrieves Pass from database by ID and throws exception if ID is invalid.
     * UpdatePassRequest object is created from client Put Http request with JSON; PassId, length to update and
     * weather or not to change activation date as a parameter. Activation start date change based on the condition and
     * update to the database.
     * @param updatePassRequest
     * @return
     * @throws ApiRequestException
     */
    public Pass updatePassLength(UpdatePassRequest updatePassRequest){
        String passId = updatePassRequest.getPassId();
        Boolean updateActivationDate = updatePassRequest.getIsUpdateActivationDate();
        int passLength = updatePassRequest.getUpdateLength();

        Pass pass = passRepository.findById(passId).orElseThrow(() ->{
            throw new ApiRequestException("Pass couldn't found for id: " + passId);
        });

        PassActivationRespond passActivationRespond = checkPassActive(pass.getId());


        if (passActivationRespond.getIsActive()){
            if (updateActivationDate){
                pass.setActivationDate(Instant.now());
                pass.setPassLength(passLength);

                return passRepository.save(pass);
            } else {
                pass.setPassLength(passLength);

                return passRepository.save(pass);
            }
        } else{
            pass.setActivationDate(Instant.now());
            pass.setPassLength(passLength);

            return passRepository.save(pass);
        }
    }

    /**
     * Delete the Pass from the database and throws exception if the PassId is invalid.
     * @param passId
     * @throws ApiRequestException
     */
    public void cancelPass(String passId){
        Pass pass = passRepository.findById(passId).orElseThrow(() ->{
            throw new ApiRequestException("Pass couldn't found for id: " + passId);
        });

        passRepository.delete(pass);
    }


}
