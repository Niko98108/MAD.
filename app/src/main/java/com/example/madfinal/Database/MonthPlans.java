package com.example.madfinal.Database;

import android.provider.BaseColumns;


public final class MonthPlans {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private MonthPlans() {}

    /* Inner class that defines the table contents */
    public static class MnthTask implements BaseColumns {
        public static final String TABLE_NAME = "Monthly_Task";
        public static final String COLUMN_1 = "nick_name";
        public static final String COLUMN_2 = "month";

    }
}


