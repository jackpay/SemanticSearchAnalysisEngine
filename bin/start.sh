#!/bin/bash
############################################################################################################################################################
######
###### Script used to set call Nutch crawl when polled by the user. Once finished the results are sent to the polling service for
###### classification by Method51 and subsequent re-indexing into Solr with their updated class meta-data.
######
###### Dependent on the parameters set, the sequence followed are: 1) send new docs to Method51 2) Poll the output tables for the records 3) re-index into Solr with new meta-data 4) Clear the MySQL tables 5) Email the analyst.
######
############################################################################################################################################################

