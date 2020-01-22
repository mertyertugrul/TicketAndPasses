package com.mertugrul.TicketAndPasses.controller;

import com.mertugrul.TicketAndPasses.model.PassActivationRespond;
import com.mertugrul.TicketAndPasses.model.PassDatasource;
import com.mertugrul.TicketAndPasses.model.UpdatePassRequest;
import com.mertugrul.TicketAndPasses.model.VerificationRespond;
import com.mertugrul.TicketAndPasses.model.entity.Pass;
import com.mertugrul.TicketAndPasses.service.PassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/leisure-pass")
public class PassController {

    @Autowired
    private PassService passService;

    @PostMapping
    public Pass addPass(@NonNull @RequestBody PassDatasource createPassDatasource){
        return passService.createPass(createPassDatasource);
    }

    @GetMapping
    public List<Pass> getAllPasses(){
        return passService.getAllPasses();
    }

    @GetMapping(path = "{id}")
    public Pass getPassById(@PathVariable("id") String id){
        return passService.getPassById(id);
    }

    @GetMapping(path = "{id}/activation")
    public PassActivationRespond isPassActive(@PathVariable("id") String id){
        return passService.checkPassActive(id);
    }

    @GetMapping(path = "{id}/vendor/{vendor_id}")
    public VerificationRespond verifyPass(@PathVariable("id") String passId, @PathVariable("vendor_id") Long vendorId){
        return passService.verifyPass(passId, vendorId);
    }

    @PutMapping(path = "update")
    public Pass updatePassLength(@NotNull @RequestBody UpdatePassRequest updatePassRequest){
        return passService.updatePassLength(updatePassRequest);
    }

    @DeleteMapping("{passId}/deletion")
    public Pass CancelPass(@PathVariable("passId") String passId){
        return passService.cancelPass(passId);
    }

}
