INSERT INTO `user` VALUES ('Advertiser',2,'stefan@gmail.com','Stefan','','Stefanovic','$2a$10$AqdmQdtkYV4S4TgqWPZYJu5DvS1ruFG6u2XR72KhSS9CJw2HI4Ct.','stefan','89c631c2-c7bb-4483-ab36-4b8d4b0b8960',NULL,NULL),('Customer',3,'milos@gmail.com','Milos','','Milosevic','$2a$10$GnY9n.Ihknyx69.YzByF7eWi6VVD2hahAYvA8wgLYdbeNUgDmS.46','milos','e6b313b8-dc98-4c24-bb60-97fca4ed3631',NULL,NULL),('Moderator',4,'mitar@gmail.com','Mitar','','Mitrovic','$2a$10$16W90eqrR.df7W8K0j4R0edWUDzEKEezljeICbMebJJV0VjJx0w4a','mitar','175f3255-ae6a-406b-9193-c2a321847a24',NULL,NULL),('Customer',5,'cone@yahoo.com','Nemanja','\0','Zunic','$2a$10$jpOCnWBS//RFtak3lxqC..b4nGVYgPgbfEn9hGQQICwwc5TpMjNHW','cone','82ace1b5-7c51-4eeb-adff-5a123d831754',NULL,NULL),('Advertiser',6,'stefan@yahoo.com','Stefan','\0','Plazic','$2a$10$2LUkvuUHrkKxXqyxMSwxlutCjNZYVVLEUVkfBr1ZhfLDPa/NCuv8G','stefi','44dd8f9a-da80-43f0-bd55-22a60970eb18',NULL,NULL),('Advertiser',7,'milen@yahoo.com','Miodrag','\0','Vilotijevic','$2a$10$IzmEqKFfFHWzuPhzA4XGQO11Ha99Nrs52XPXqPwiA1qg0Ncyy1F0C','mile','bfbaf4d1-4ce7-4d40-8435-71ee0e5017d6',NULL,NULL);

INSERT INTO `user_authority` VALUES (2,4,2),(3,3,3),(4,2,4),(5,3,5),(6,4,6),(7,4,7);

INSERT INTO `company` VALUES (1,'adresa1','name1','','OLD',2),(2,'adresa2','name2','\0','NEW',6);

INSERT INTO `estate` VALUES (1,'uzicka 67',150,'beograd','zemun','podno grejanje','kuca na sprat',30000,'kosilica',2),(2,'sumadijska',150,'uzice','belo groblje','p','p',3000,'p',6),(3,'adresa3',150,'sabac','centar','p','p',25000,'k',6);

INSERT INTO `image` VALUES (1,'slika1-1',1),(2,'slika2-1',1),(3,'slika3-1',1),(4,'slika1-3',3);

INSERT INTO `advertisement` VALUES (1,'2017-02-01 12:00:00','2016-12-01 00:00:00','OPEN',2,1,NULL),(2,'2016-09-01 12:00:00','2015-12-01 00:00:00','REPORTED',6,2,NULL),(3,'2016-10-10 12:00:00','2016-03-03 00:00:00','OPEN',6,3,NULL), (4,'2017-12-12 12:00:00','2016-03-03 00:00:00','OPEN',7,1,NULL);

INSERT INTO `call_to_company` VALUES (1,'2016-10-10 12:00:00',6,7);

INSERT INTO `comment` VALUES (1,'komentar1',2,1,5);

INSERT INTO `favourites` VALUES (1,3,1),(2,3,2);

INSERT INTO `notification` VALUES (1,'2016-12-22 20:47:44','message','\0','NEW','zdravo',1,5,2),(2,'2016-12-22 20:51:04','message','\0','NEW','odgovor',1,2,5);

INSERT INTO `report` VALUES (1,'Picture is rude','','NEW',2,5);

INSERT INTO `report` VALUES (2, 'House is mine', 0, 'OLD', 3, 3);

INSERT INTO `view` VALUES (1,'2016-12-22 12:00:00',1,3);