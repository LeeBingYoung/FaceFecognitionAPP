package com.bs.demo.myapplication.bean;

import java.util.List;

/**
 * Author :     zjw
 * Date :       2018/2/24
 * Description:
 */

public class KQBean {

    /**
     * result : [{"uid":"1","scores":[23.007942199707],"group_id":"group1","user_info":"user's info"}]
     * result_num : 1
     * log_id : 3741289481022418
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
         * uid : 1
         * scores : [23.007942199707]
         * group_id : group1
         * user_info : user's info
         */

        private String uid;
        private String group_id;
        private String user_info;
        private List<Double> scores;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getUser_info() {
            return user_info;
        }

        public void setUser_info(String user_info) {
            this.user_info = user_info;
        }

        public List<Double> getScores() {
            return scores;
        }

        public void setScores(List<Double> scores) {
            this.scores = scores;
        }
    }
}
