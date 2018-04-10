package com.bs.demo.myapplication.bean;

import java.util.List;

/**
 * Author :     zjw
 * Date :       2018/2/24
 * Description:
 */

public class XBBean {

    /**
     * result_num : 1
     * result : [{"location":{"left":98,"top":191,"width":270,"height":245},"face_probability":1,"rotation_angle":1,"yaw":-0.027360778301954,"pitch":15.992117881775,"roll":1.0433450937271,"gender":"female","gender_probability":0.99979132413864}]
     * log_id : 3441333102022417
     */

    private int result_num;
    private long log_id;
    private List<ResultBean> result;

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * location : {"left":98,"top":191,"width":270,"height":245}
         * face_probability : 1
         * rotation_angle : 1
         * yaw : -0.027360778301954
         * pitch : 15.992117881775
         * roll : 1.0433450937271
         * gender : female
         * gender_probability : 0.99979132413864
         */

        private LocationBean location;
        private double face_probability;
        private int rotation_angle;
        private int glasses;
        private int age;
        private int expression;
        private double yaw;
        private double pitch;
        private double roll;
        private double beauty;
        private String gender;
        private double gender_probability;
        private String race;
        private String faceshape;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public double getFace_probability() {
            return face_probability;
        }

        public void setFace_probability(double face_probability) {
            this.face_probability = face_probability;
        }

        public int getRotation_angle() {
            return rotation_angle;
        }

        public void setRotation_angle(int rotation_angle) {
            this.rotation_angle = rotation_angle;
        }

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }

        public double getPitch() {
            return pitch;
        }

        public void setPitch(double pitch) {
            this.pitch = pitch;
        }

        public double getRoll() {
            return roll;
        }

        public void setRoll(double roll) {
            this.roll = roll;
        }

        public String getRace() {
//            if(race.equals("yellow")) return "黄种人";
//            else if(race.equals("white")) return "白种人";
//            else if(race.equals("black")) return "黑种人";
//            else if(race.equals("arabs")) return "阿拉伯人";
//            else return "无法确定人种";
            return race;
        }

        public void setRace(String race) {
            this.race = race;
        }

        public String getGender() {
//            if(gender.equals("female")) return "女性";
//            else if(gender.equals("male")) return "男性";
//            else return "性别无法确定";
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getExpression() {
//            if(expression==0) return "表情为不笑";
//            else if(expression==1) return "表情为微笑";
//            else if(expression==2) return "表情为大笑";
//            else return "笑容无法确定";
            return expression;
        }

        public void setExpression(int expression) {
            this.expression = expression;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getBeauty() {
            return (int)beauty;
        }

        public void setBeauty(double beauty) {
            this.beauty = beauty;
        }

        public double getGender_probability() {
            return gender_probability;
        }

        public void setGender_probability(double gender_probability) {
            this.gender_probability = gender_probability;
        }

        public static class LocationBean {
            /**
             * left : 98
             * top : 191
             * width : 270
             * height : 245
             */

            private int left;
            private int top;
            private int width;
            private int height;

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
