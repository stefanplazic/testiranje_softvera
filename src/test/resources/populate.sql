#dtype, id, email, first_name, is_verified, last_name, password, username, verify_code, account, company
INSERT INTO `user` VALUES 
('Advertiser',2,'stefan@gmail.com','Stefan','','Stefanovic','$2a$10$AqdmQdtkYV4S4TgqWPZYJu5DvS1ruFG6u2XR72KhSS9CJw2HI4Ct.','stefan','89c631c2-c7bb-4483-ab36-4b8d4b0b8960',NULL,NULL),
('Customer',3,'milos@gmail.com','Milos','','Milosevic','$2a$10$GnY9n.Ihknyx69.YzByF7eWi6VVD2hahAYvA8wgLYdbeNUgDmS.46','milos','e6b313b8-dc98-4c24-bb60-97fca4ed3631',NULL,NULL),
('Moderator',4,'mitar@gmail.com','Mitar','','Mitrovic','$2a$10$16W90eqrR.df7W8K0j4R0edWUDzEKEezljeICbMebJJV0VjJx0w4a','mitar','175f3255-ae6a-406b-9193-c2a321847a24',NULL,NULL),
('Customer',5,'cone@yahoo.com','Nemanja','','Zunic','$2a$10$jpOCnWBS//RFtak3lxqC..b4nGVYgPgbfEn9hGQQICwwc5TpMjNHW','cone','82ace1b5-7c51-4eeb-adff-5a123d831754',NULL,NULL),
('Advertiser',6,'stefan@yahoo.com','Stefan','','Plazic','$2a$10$2LUkvuUHrkKxXqyxMSwxlutCjNZYVVLEUVkfBr1ZhfLDPa/NCuv8G','stefi','44dd8f9a-da80-43f0-bd55-22a60970eb18',NULL,NULL),
('Advertiser',7,'milen@yahoo.com','Miodrag','','Vilotijevic','$2a$10$IzmEqKFfFHWzuPhzA4XGQO11Ha99Nrs52XPXqPwiA1qg0Ncyy1F0C','mile','bfbaf4d1-4ce7-4d40-8435-71ee0e5017d6',NULL,NULL);

#id, authority_id, user_id
INSERT INTO `user_authority` VALUES 
(2,4,2),
(3,3,3),
(4,2,4),
(5,3,5),
(6,4,6),
(7,4,7);

#id, address, name, on_hold, status, owner
INSERT INTO `company` VALUES 
(1,'Svetozara Markovica 10, Beograd','Real Estate','','OLD',2),
(2,'Mise Dimitrijevica 3, Novi Sad','Tim 7 Co','','OLD',6),
(3,'Zikice Jovanovica 15, Loznica','Lo Estate Co','\0','NEW',7);

#dtype, id, email, first_name, is_verified, last_name, password, username, verify_code, account, company
INSERT INTO `user` VALUES
('Advertiser', 8, 'nikola@gmail.com', 'Nikola','', 'Nikolic', '$2a$10$Ccb0W7ZiyzMPN6o.KZhzMOenvBUC6vGpRcRPkOw1ZVwZj3QsyefMi', 'nikola', '06021325-9e27-4b6c-a7d2-9a42e3bf3cb9',NULL,2),
('Advertiser', 9, 'zoran@gmail.com', 'Zoran', '', 'Zoranic', '$2a$10$iks3.e7TIULoTKXWqgFYNOusyFocf/kQyfIvvrU.Oivtb2ROOAXWa', 'zoran', 'e1b78e13-d84e-458b-acd8-5f63ddc35581', NULL, 2),
('Advertiser', 10, 'janko@gmail.com', 'Janko', '', 'Jankovic', '$2a$10$zCRiKJ3.C7zA2dIcLYkeROxCkPUEKrmaFC5bjEGJcg7Tj51Y1pKDW', 'janko', 'cb080c5a-41e6-4802-a660-02bd24ef809d', NULL, 3),
('Advertiser', 11, 'jovan@gmail.com', 'Jovan', '', 'Jovanovic', '$2a$10$RATcket55WGs6/bsAcNcIORtJaHuIyKm3cQKjj5hzpWeMH2Wn7UQO', 'jovan', '0b029cb4-5a4c-46b5-b2cb-560594f4a9e5', NULL, 3),
('Advertiser', 12, 'marko@gmail.com', 'Marko', '', 'Markovic', '$2a$10$CXIL4Qdjfslo8CbiL85CMOGmbocrExL/uHK/LZ53SxeMpPoPpx6M2', 'marko', '1996ae6d-7342-4549-ab28-d3de55ec5f42', NULL, 3),
('Advertiser', 13, 'petar@gmail.com', 'Petar', '', 'Petrovic', '$2a$10$ygJaaaS.NJFxnEqOdMxGZe3e4TtbaFZktwKvgtMGNP3APNsRi6wlK', 'petar', 'a74a7103-b2bc-459f-b148-c5d1298d8b2a', NULL, NULL),
('Advertiser', 14, 'ivan@gmail.com', 'Ivan', '', 'Ivanovic', '$2a$10$RFTepatDvpqb.prjGIzJ..CZII8boygWe07AqAW/nhcXzKBT8hKo.', 'ivan', '4a838325-e84a-46b7-b0bd-98c210d1bf25', NULL, NULL);

#id, authority_id, user_id
INSERT INTO `user_authority` VALUES 
(8,4,8),
(9,4,9),
(10,4,10),
(11,4,11),
(12,4,12),
(13,4,13),
(14,4,14);

#id, address, area, city, city_part, heating_system, name, price, technical_equipmnet, owner_id
INSERT INTO `estate` VALUES 
(1,'Uzicka 67',150,'Beograd','Savski Venac','Central','Family House',75000,'',2),
(2,'Veljka Petrovica 3', 30, 'Sremska Mitrovica', 'Centar', 'Firewood', 'Small Apartment', 20000, '', 7),
(3,'Sumadijska 62', 75,'Uzice','Belo Groblje','Gas','Zunic Estate',30000,'',8),
(4,'Prvomajska 10',100,'Sabac','Centar','Firewood','Stajcic Estate',35000,'',9),
(5,'Milosa Obilica 5',50,'Sombor','Centar','Electric','Family House',50000,'',10),
(6,'Mice Zarica 2',15,'Kosjeric','','Central','Comfy Place',15500,'',11),
(7,'Zelengorska 7',45,'Nis','Crveni Pevac','Central','Penthouse in Nis',100000,'',12),
(8,'Drinske Divizije 2',15,'Zlatibor','Rujno','Firewood','Winter Lodge',70000,'',13),
(9,'Tolstojeva 1',35,'Subotica','Centar','Gas','Subotica Apartment',35000,'',14);

#id, url, estate
INSERT INTO `image` VALUES 
(1,'https://cdn.houseplans.com/product/o2d2ui14afb1sov3cnslpummre/w560x373.jpg?v=9',1),
(2,'http://www.achristmasstoryhouse.com/images/homepagehouse.jpg',2),
(3,'https://s-media-cache-ak0.pinimg.com/736x/1e/cb/e8/1ecbe8d4a5b9b615d8a2fdf9197c4817.jpg',3),
(4,'http://cdn.freshome.com/wp-content/uploads/2008/03/apartment.jpg',4),
(5,'http://www.rdrymarov.cz/editor/filestore/Image/Largo/Largo_122/L122_01_33629.jpg', 5),
(6,'http://visitserbia.eu/wp-content/uploads/2013/04/Kosjeric_2.jpg',6),
(7,'http://b-i.forbesimg.com/trulia/files/2013/06/penthouse-plaza-hotel-for-sale-1.png',7),
(8,'http://www.serbia.com/wp-content/uploads/2015/12/zlatibor-zima-tida-travel4.jpg',8),
(9,'http://www.aluxurytravelblog.com/wp-content/uploads/2014/04/Kingston-apartment-kitchen.jpg',9);

#id, expiry_date, publication_date, state, advertiser, estate, soldto
INSERT INTO `advertisement` VALUES 
(1,'2017-02-01 17:00:00','2016-12-01 00:00:00','OPEN',2,1,NULL),
(2,'2016-09-01 14:30:00','2015-12-01 00:00:00','REPORTED',6,2,NULL),
(3,'2016-10-10 20:00:00','2016-11-03 00:00:00','OPEN',6,3,NULL),
(4,'2017-12-12 22:00:00','2016-10-05 00:00:00','OPEN',7,1,NULL),
(5,'2017-05-10 10:00:00','2017-01-01 00:00:00','OPEN',8,4,NULL),
(6,'2017-10-01 12:10:00','2017-01-01 00:00:00','REPORTED',9,5,NULL),
(7,'2018-01-01 00:00:00','2017-01-15 00:00:00','OPEN',10,6,NULL),
(8,'2017-01-05 00:00:00','2016-12-15 00:00:00','EXPIRED',11,7,NULL),
(9,'2017-02-05 14:00:00','2016-12-10 00:00:00','RENTED',12,8,NULL),
(10,'2017-03-05 18:00:00','2016-12-10 00:00:00','REPORTED',13,8,NULL),
(11,'2017-09-16 00:00:00','2016-07-19 00:00:00','OPEN',14,9,NULL);

#id, date_of_call, fromadvrt, toadvrt
INSERT INTO `call_to_company` VALUES 
(1,'2017-01-28 14:00:00',6,13),
(2,'2017-01-25 14:00:00',7,13),
(3,'2017-01-27 13:30:00',6,14);

#id, data, time, advertisement, user
INSERT INTO `comment` VALUES 
(1,'komentar1',2,1,5);

#id, customer, estate
INSERT INTO `favourites` VALUES 
(1,3,1),
(2,3,2);

#id, made, n_type, seen, status, text, advertisement, from_user_id, to_user_id
INSERT INTO `notification` VALUES 
(1,'2016-12-22 20:47:44','message','\0','NEW','zdravo',1,5,2),
(2,'2016-12-22 20:51:04','message','\0','NEW','odgovor',1,2,5);

#id, message, on_hold, status, advertisement, user
INSERT INTO `report` VALUES 
(1,'Picture is rude','','NEW',2,5),
(2,'House is mine', '','NEW',3,3);

#id, time, advert, viewer
INSERT INTO `view` VALUES 
(1,'2017-01-25 12:45:23',3,1),
(2,'2017-01-22 17:32:11',4,1),
(3,'2017-01-21 11:02:06',7,1),
(4,'2017-01-21 11:00:44',9,1),
(5,'2017-01-20 20:11:23',11,1),
(6,'2017-01-28 22:06:56',1,2),
(7,'2017-01-28 11:22:33',2,2),
(8,'2017-01-28 11:20:16',4,2),
(9,'2017-01-28 11:19:12',5,2),
(10,'2017-01-28 11:16:00',6,2),
(11,'2017-01-27 08:24:36',8,4),
(12,'2017-01-26 14:33:01',10,4),
(13,'2017-01-26 07:45:47',11,4),
(14,'2017-01-28 12:21:51',6,5),
(15,'2017-01-25 13:04:39',10,5),
(16,'2017-01-28 20:24:57',1,6),
(17,'2017-01-28 20:25:32',2,6),
(18,'2017-01-28 20:27:15',3,6),
(19,'2017-01-28 20:45:01',4,6),
(20,'2017-01-27 17:23:20',5,6),
(21,'2017-01-27 17:20:12',7,6),
(22,'2017-01-27 12:15:03',10,6),
(23,'2017-01-28 12:33:20',4,7),
(24,'2017-01-28 12:30:10',8,7),
(25,'2017-01-25 17:44:16',10,7),
(26,'2017-01-25 17:42:19',11,7),
(27,'2017-01-28 09:51:00',3,8),
(28,'2017-01-28 09:47:26',6,8),
(29,'2017-01-28 09:43:57',9,8),
(30,'2017-01-28 09:32:02',11,8),
(31,'2017-01-28 11:29:34',2,9),
(32,'2017-01-28 11:14:24',4,9),
(33,'2017-01-28 10:16:33',6,9),
(34,'2017-01-28 10:13:56',9,9),
(35,'2017-01-27 12:45:48',10,9);