#registracija usera svakom je sifra ista kao first - username
set foreign_key_checks = 0;

INSERT INTO `project_testing`.`user` (`dtype`, `email`, `first_name`, `is_verified`, `last_name`, `password`, `username`, `verify_code`) VALUES ('Advertiser', 'stefan@gmail.com', 'Stefan', 1, 'Stefanovic', '$2a$10$AqdmQdtkYV4S4TgqWPZYJu5DvS1ruFG6u2XR72KhSS9CJw2HI4Ct.', 'stefan', '89c631c2-c7bb-4483-ab36-4b8d4b0b8960');
INSERT INTO `project_testing`.`user_authority` (`authority_id`, `user_id`) VALUES (4, 2);
INSERT INTO `project_testing`.`user` (`dtype`, `email`, `first_name`, `is_verified`, `last_name`, `password`, `username`, `verify_code`) VALUES ('Customer', 'milos@gmail.com', 'Milos', 1, 'Milosevic', '$2a$10$GnY9n.Ihknyx69.YzByF7eWi6VVD2hahAYvA8wgLYdbeNUgDmS.46', 'milos', 'e6b313b8-dc98-4c24-bb60-97fca4ed3631');
INSERT INTO `project_testing`.`user_authority` (`authority_id`, `user_id`) VALUES (3, 3);
INSERT INTO `project_testing`.`user` (`dtype`, `email`, `first_name`, `is_verified`, `last_name`, `password`, `username`, `verify_code`) VALUES ('Moderator', 'mitar@gmail.com', 'Mitar', 1, 'Mitrovic', '$2a$10$16W90eqrR.df7W8K0j4R0edWUDzEKEezljeICbMebJJV0VjJx0w4a', 'mitar', '175f3255-ae6a-406b-9193-c2a321847a24');
INSERT INTO `project_testing`.`user_authority` (`authority_id`, `user_id`) VALUES (2, 4);

#kreirati estate i vezati za advertisera - Stefan Stefanovic
INSERT INTO `project_testing`.`estate` (`address`, `area`, `city`, `city_part`, `heating_system`, `name`, `price`, `technical_equipment`, `owner_id`) VALUES ('uzicka 67', '150', 'beograd', 'zemun', 'podno grejanje', 'kuca na sprat', '30000', 'kosilica', 2);

#kreirati advertisement i vezati za advertisera - Stefan Stefanovic i estate - kuca na sprat
INSERT INTO `project_testing`.`advertisement` (`expiry_date`, `publication_date`, `state`, `advertiser`, `estate`) VALUES ('2017-02-01 12:00:00.000000', '2016-12-01 00:00:00.000000', 'OPEN', 2, 1);