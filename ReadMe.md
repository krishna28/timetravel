# TimeTravel Documentation

Task to expose rest api to save user travel details with some creteria and making sure user can't save same journey on database based on same date, journey id and place.

### Tech
Used tech stack:

* Spring boot
* H2 DB
* Java 1.8
### API EXPOSED

#### Save timeTravel
/api/timetravel
HTTP POST REQUEST
```
{
    "timeTravelId":{
         "personalGalacticIdentifier":"ABC12",
        "place":"DELHI",
        "date":"12-05-2020"
    }
}
```

#### getall timeTravel
/api/timetravel
HTTP GET REQUEST

#### get allByDate timeTravel
/api/timetravel/allByDate/{date}
HTTP GET REQUEST

#### get allByPersonalGalacticIdentifier timeTravel
/api/timetravel/allByPGI/{pgi}
HTTP GET REQUEST

#### getbyid (id is a foreign key using multiple properties) timeTravel
/api/timetravel/{pgi}/{place}/{date}
HTTP GET REQUEST

### All endpoint tested via integration test


