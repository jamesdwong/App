package uk.ac.livjm.cms.cmpjwong.routefinder;

public class SuggestGetSet {
    String crsCode;
    String stationName;


    public SuggestGetSet(String crsCode, String stationName){
        this.setCode(crsCode);
        this.setName(stationName);
    }
    public String getCode() {
        return crsCode;
    }

    public void setCode(String crsCode) {
        this.crsCode = crsCode;
    }

    public String getName() {
        return stationName;
    }

    public void setName(String stationName) {
        this.stationName = stationName;
    }

}