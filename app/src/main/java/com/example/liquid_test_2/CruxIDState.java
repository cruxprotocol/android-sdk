package com.example.liquid_test_2;


class CruxIDState {
    public final String cruxID;
    public final CruxIDRegistrationStatus status;

    CruxIDState(String cruxID, CruxIDRegistrationStatus status){
        this.cruxID = cruxID;
        this.status = status;
    }
}
