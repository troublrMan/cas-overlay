cas-management-overlay
======

Just a cas-management overaly with external configurations

### build and deploy

- `sync_ect_cas` ant target copies under `/etc/cas/` the content of `etc` folder.
- `backup_json_services` ant target will backup the services 
- `tomcat_deploy` stops tomcat, builds the war, and copies it under webapps


