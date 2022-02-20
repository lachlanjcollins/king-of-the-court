package com.lachlan.kingofthecourt.util;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Class which enables storing of a date object in Android Room.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
public class Converters {

    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }
}
