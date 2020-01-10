DELETE FROM leisure_pass_test.customer WHERE customer.customer_id in (1,2,3);

DELETE FROM leisure_pass_test.vendor WHERE vendor.vendor_id in (1,2,3);

INSERT INTO leisure_pass_test.customer (customer_id, address, full_name) VALUE
  (1, "New York, USA", "Jason Brown"),
  (2, "London, UK", "Mary Poppins"),
  (3, "Ankara, Turkey", "Mert Ertugrul");


INSERT INTO leisure_pass_test.vendor (vendor_id, address, vendor_name) VALUE
  (1, "New York, USA", "Empire State Building"),
  (2, "London, UK", "London Eye"),
  (3, "Ankara, Turkey", "Atakule");