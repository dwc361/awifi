CREATE TABLE oauth_client_details(
client_id varchar2(255) NOT NULL PRIMARY KEY,
client_secret varchar2(255),
resource_ids varchar2(255),
scope varchar2(255), 
authorized_grant_types varchar2(255), 
web_server_redirect_uri varchar2(255), 
authorities  varchar2(255), 
access_token_validity NUMBER(19,0),
refresh_token_validity  NUMBER(19,0),
additional_information varchar2(255)
);

create table oauth_access_token (  
  token_id VARCHAR(255),  
  client_id varchar2(255),
  user_name varchar2(255),
  token blob,  
  authentication_id VARCHAR(255),  
  authentication blob,  
  refresh_token VARCHAR(255)  
); 

CREATE TABLE oauth_refresh_token ( 
TOKEN_ID VARCHAR(64) NOT NULL PRIMARY KEY, 
TOKEN BLOB NOT NULL, 
authentication BLOB NOT NULL);

create table oauth_code (  
  code VARCHAR(255),  
  authentication blob  
);  