--使用DBA权限用户创建用户
-- Create the user 
create user INTERTRADE
  default tablespace USERS
  temporary tablespace TEMP
  profile DEFAULT
  identified by "INTERTRADE";
-- Grant/Revoke role privileges 
grant connect to INTERTRADE;
grant dba to INTERTRADE;
grant resource to INTERTRADE;
-- Grant/Revoke system privileges 
grant unlimited tablespace to INTERTRADE;