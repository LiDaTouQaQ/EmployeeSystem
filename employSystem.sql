/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.0.18-nt : Database - employsystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`employsystem` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `employsystem`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `address_id` int(11) NOT NULL auto_increment COMMENT '地址id',
  `city` varchar(50) NOT NULL COMMENT '城市',
  `province` varchar(50) NOT NULL COMMENT '省份',
  PRIMARY KEY  (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `address` */

insert  into `address`(`address_id`,`city`,`province`) values (1,'南京','江苏'),(2,'淮安','江苏'),(3,'上海市','上海');

/*Table structure for table `announcement` */

DROP TABLE IF EXISTS `announcement`;

CREATE TABLE `announcement` (
  `a_id` int(10) unsigned NOT NULL auto_increment COMMENT '公告id',
  `a_date` date NOT NULL COMMENT '公告时间',
  `a_object` char(1) NOT NULL default 'E' COMMENT '公告发布对象',
  `a_msg` varchar(500) NOT NULL COMMENT '公告内容',
  `o_id` int(11) NOT NULL COMMENT '对象id',
  PRIMARY KEY  (`a_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `announcement` */

insert  into `announcement`(`a_id`,`a_date`,`a_object`,`a_msg`,`o_id`) values (1,'2023-11-27','C','今天休假啦',-1),(2,'2023-11-27','E','你上班不要摸鱼',1),(3,'2023-11-25','D','部门全体员工加新了',3),(4,'2023-11-20','E','给你加薪了，晚上来我办公室',2),(5,'2023-11-28','C','咱们公司上市了',-1),(6,'2023-11-28','C','加新',-1);

/*Table structure for table `bonus` */

DROP TABLE IF EXISTS `bonus`;

CREATE TABLE `bonus` (
  `bonus_id` int(10) unsigned NOT NULL auto_increment COMMENT '奖励id',
  `bonus_info_id` int(11) NOT NULL COMMENT '奖励详细id',
  `bonus_date` date NOT NULL COMMENT '奖励时间',
  `emp_id` int(11) NOT NULL COMMENT '奖励员工id',
  PRIMARY KEY  (`bonus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bonus` */

insert  into `bonus`(`bonus_id`,`bonus_info_id`,`bonus_date`,`emp_id`) values (1,1,'2023-05-04',1),(2,3,'2022-12-29',1),(3,3,'2023-11-28',1),(4,3,'2023-11-28',5);

/*Table structure for table `bonus_info` */

DROP TABLE IF EXISTS `bonus_info`;

CREATE TABLE `bonus_info` (
  `bonus_info_id` int(10) unsigned NOT NULL auto_increment COMMENT '奖金详细id',
  `bonus_name` varchar(200) NOT NULL COMMENT '奖金内容',
  `bonus_money` decimal(10,0) NOT NULL COMMENT '奖金金额',
  PRIMARY KEY  (`bonus_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `bonus_info` */

insert  into `bonus_info`(`bonus_info_id`,`bonus_name`,`bonus_money`) values (1,'全勤奖','500'),(2,'专利奖','2000'),(3,'重大贡献奖','5000');

/*Table structure for table `check_work` */

DROP TABLE IF EXISTS `check_work`;

CREATE TABLE `check_work` (
  `cw_id` int(10) unsigned NOT NULL auto_increment COMMENT '考勤id',
  `emp_id` int(11) NOT NULL COMMENT '打卡人',
  `check_date` datetime NOT NULL COMMENT '打卡时间',
  `check_state` tinyint(1) NOT NULL default '0' COMMENT '打卡状态',
  PRIMARY KEY  (`cw_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `check_work` */

insert  into `check_work`(`cw_id`,`emp_id`,`check_date`,`check_state`) values (1,1,'2023-11-01 00:00:00',1),(5,1,'2023-11-28 15:11:07',-1);

/*Table structure for table `depart` */

DROP TABLE IF EXISTS `depart`;

CREATE TABLE `depart` (
  `depart_id` int(10) unsigned NOT NULL auto_increment COMMENT '部门id',
  `depart_name` varchar(50) NOT NULL COMMENT '部门名称',
  `depart_father_id` int(11) default NULL COMMENT '父部门id',
  PRIMARY KEY  (`depart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `depart` */

insert  into `depart`(`depart_id`,`depart_name`,`depart_father_id`) values (0,'Boss部',0),(1,'市场部',0),(2,'营销部',0),(3,'人事部',0),(4,'人事校招部',3),(5,'技术部',0),(6,'原神项目部',5),(7,'王者荣耀项目部',5),(8,'浙江省营销部',2),(10,'后勤部',1);

/*Table structure for table `employee_info` */

DROP TABLE IF EXISTS `employee_info`;

CREATE TABLE `employee_info` (
  `emp_id` int(10) unsigned NOT NULL auto_increment COMMENT '员工id',
  `emp_name` varchar(20) NOT NULL COMMENT '员工姓名',
  `phone` varchar(13) NOT NULL COMMENT '员工手机号',
  `address_id` int(11) NOT NULL COMMENT '地址id',
  `address_detail` varchar(200) default NULL COMMENT '详细地址',
  `card` char(19) NOT NULL COMMENT '银行卡号',
  `hiredate` date NOT NULL default '2020-02-22' COMMENT '入职时间',
  `password` varchar(20) NOT NULL default '123456' COMMENT '密码',
  PRIMARY KEY  (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `employee_info` */

insert  into `employee_info`(`emp_id`,`emp_name`,`phone`,`address_id`,`address_detail`,`card`,`hiredate`,`password`) values (1,'李小头','14444444',1,'浦口马路牙子','320804200207163541','2018-12-12','123456'),(2,'李大头','123456789',1,'翔宇大道','8520156374','2023-11-28','123456'),(3,'张三','11111111',2,'南京路1号','25150411111','2020-02-22','123456'),(4,'王五','5524524',2,'南京路2号','85441161651','2020-02-22','123456'),(5,'六六','2587412',1,'浦口','781161','2023-11-28','123456'),(6,'1111','1232321',1,'dasdas','311343432','2023-11-28','123456');

/*Table structure for table `employeeincompany` */

DROP TABLE IF EXISTS `employeeincompany`;

CREATE TABLE `employeeincompany` (
  `ec_id` int(10) unsigned NOT NULL auto_increment COMMENT '员工在公司的信息',
  `emp_id` int(11) NOT NULL COMMENT '员工id',
  `depart_id` int(11) NOT NULL default '0' COMMENT '部命id',
  `job_id` int(11) NOT NULL default '0' COMMENT '工作id',
  `work_state` tinyint(1) NOT NULL default '1' COMMENT '在职状态',
  `sup_id` int(11) default '0' COMMENT '上级id',
  PRIMARY KEY  (`ec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `employeeincompany` */

insert  into `employeeincompany`(`ec_id`,`emp_id`,`depart_id`,`job_id`,`work_state`,`sup_id`) values (1,1,0,1,1,0),(2,2,5,2,1,1),(3,3,6,15,1,2),(4,4,8,11,0,0),(5,5,0,15,1,1),(6,6,5,13,0,2);

/*Table structure for table `expense` */

DROP TABLE IF EXISTS `expense`;

CREATE TABLE `expense` (
  `exp_id` int(10) unsigned NOT NULL auto_increment COMMENT '报销单id',
  `in_emp_id` int(11) NOT NULL COMMENT '报销人',
  `out_emp_id` int(11) NOT NULL COMMENT '审批人',
  `exp_content` varchar(200) NOT NULL COMMENT '报销内容',
  `exp_money` decimal(10,0) NOT NULL COMMENT '报销金额',
  `exp_date` date NOT NULL COMMENT '报销时间',
  `is_pass` tinyint(4) NOT NULL default '0' COMMENT '是否通过',
  PRIMARY KEY  (`exp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `expense` */

insert  into `expense`(`exp_id`,`in_emp_id`,`out_emp_id`,`exp_content`,`exp_money`,`exp_date`,`is_pass`) values (1,1,1,'给点钱画画','2000','2023-11-28',1),(2,1,2,'给点钱画画','5222','2023-11-28',1);

/*Table structure for table `job` */

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
  `job_id` int(10) unsigned NOT NULL auto_increment COMMENT '职位id',
  `job_name` varchar(50) NOT NULL COMMENT '职位名称',
  `job_grade` tinyint(4) NOT NULL default '0' COMMENT '职位等级',
  `salary` decimal(10,0) NOT NULL COMMENT '职位薪资',
  PRIMARY KEY  (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `job` */

insert  into `job`(`job_id`,`job_name`,`job_grade`,`salary`) values (1,'boss',10,'50000'),(2,'首席工程师',9,'30000'),(3,'总裁',9,'30000'),(4,'副总裁',8,'25000'),(5,'首席架构师',9,'30000'),(6,'高级工程师',8,'25000'),(7,'中级工程师',7,'15000'),(8,'初级工程师',6,'12000'),(9,'主管',7,'20000'),(10,'经理',6,'15000'),(11,'组长',5,'8000'),(12,'HR',4,'6000'),(13,'Java开发',5,'8000'),(14,'前端开发',5,'8000'),(15,'实习生',3,'4000');

/*Table structure for table `punish` */

DROP TABLE IF EXISTS `punish`;

CREATE TABLE `punish` (
  `punish_id` int(10) unsigned NOT NULL auto_increment COMMENT '惩罚id',
  `punish_info_id` int(11) NOT NULL COMMENT '惩罚详细id',
  `punish_date` date NOT NULL COMMENT '惩罚时间',
  `emp_id` int(11) NOT NULL COMMENT '惩罚人',
  PRIMARY KEY  (`punish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `punish` */

insert  into `punish`(`punish_id`,`punish_info_id`,`punish_date`,`emp_id`) values (1,1,'2023-08-23',1),(2,2,'2023-11-28',2),(3,1,'2023-11-28',5);

/*Table structure for table `punish_info` */

DROP TABLE IF EXISTS `punish_info`;

CREATE TABLE `punish_info` (
  `punish_info_id` int(10) unsigned NOT NULL auto_increment COMMENT '惩罚详细id',
  `punish_name` varchar(50) NOT NULL COMMENT '惩罚名称',
  `punish_money` decimal(10,0) NOT NULL COMMENT '惩罚金额',
  PRIMARY KEY  (`punish_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `punish_info` */

insert  into `punish_info`(`punish_info_id`,`punish_name`,`punish_money`) values (1,'缺勤','100'),(2,'迟到','50'),(3,'危害公司利益','5000'),(4,'损害公司名誉','3000'),(5,'工作失误','500');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
