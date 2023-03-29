package com.example.orangesale_02;

import com.wheelpicker.widget.PickString;

import java.util.List;
/**
 * 城市选择器自定义类
 */
public class AdministrativeMap {
    public int year;
    public List<com.wheelpicker.AdministrativeMap.Province> provinces;

    /**
     * 省
     */
    public static class Province implements PickString {
        public String name;
        public String code;
        public List<com.wheelpicker.AdministrativeMap.City> city;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 市
     */
    public static class City implements PickString{
        public String name;
        public String code;
        public List<com.wheelpicker.AdministrativeMap.Area> areas;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 区县
     */
    public static class Area implements PickString{
        public String name;
        public String code;
        public List<com.wheelpicker.AdministrativeMap.Country> countries;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 乡镇
     */
    public static class Country  implements PickString{
        public String name;
        public String code;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }
}
