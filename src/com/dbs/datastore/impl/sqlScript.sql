DROP TABLE IF EXISTS wechat_id_dbs_id;
CREATE TABLE  wechat_id_dbs_id(ID INT PRIMARY KEY, wechatID VARCHAR(255),dbsID VARCHAR(255));
INSERT INTO wechat_id_dbs_id VALUES(1, 'Tom','Tom_dbs');
INSERT INTO wechat_id_dbs_id VALUES(2, 'Jim','Jim_dbs');


DROP TABLE IF EXISTS user;
CREATE TABLE  user(userid VARCHAR(255),password VARCHAR(255));

CREATE TABLE `customer_bankacctinfo` (
  `customer_bankAcctNo` int(20) NOT NULL,
  `customer_bankAcctBal` decimal(30,2) DEFAULT NULL,
  `customer_id` int(20) DEFAULT NULL,
  `customer_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`customer_bankAcctNo`),
  UNIQUE KEY `idcustomer_bankAcctInfo_UNIQUE` (`customer_bankAcctNo`)
)


CREATE TABLE `customer_info` (
  `customer_id` int(20) NOT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `customer_mobileNo` varchar(50) DEFAULT NULL,
  `customer_weChat_openId` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_id_UNIQUE` (`customer_id`)
)

CREATE TABLE `payeelist` (
  `payee_id` int(20) NOT NULL,
  `payee_name` varchar(100) DEFAULT NULL,
  `payee_bankname` varchar(50) DEFAULT NULL,
  `payee_acctNo` int(20) DEFAULT NULL,
  `customer_bankAcctNo` int(20) DEFAULT NULL,
  PRIMARY KEY (`payee_id`),
  UNIQUE KEY `payee_id_UNIQUE` (`payee_id`)
)
CREATE TABLE `transaction` (
  `trans_id` varchar(100) NOT NULL,
  `trans_type` varchar(100) DEFAULT NULL,
  `trans_date` datetime DEFAULT NULL,
  `trans_note` varchar(200) DEFAULT NULL,
  `trans_amount` decimal(30,2) DEFAULT NULL,
  `customer_bankAcctNo` int(20) DEFAULT NULL,
  `customer_id` int(20) DEFAULT NULL,
  `payee_acctNo` int(20) DEFAULT NULL,
  `payee_bankname` varchar(50) DEFAULT NULL,
  `payee_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`trans_id`),
  UNIQUE KEY `customer_transaction_id_UNIQUE` (`trans_id`)
)

