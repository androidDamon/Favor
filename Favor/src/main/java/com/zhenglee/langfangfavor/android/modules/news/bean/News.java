package com.zhenglee.langfangfavor.android.modules.news.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhenglee on 16/4/13.
 */
public class News implements Serializable {

    /**
     * postid : BKHPMJ8600014AEE
     * hasCover : false
     * hasHead : 1
     * replyCount : 219032
     * ltitle : 广东梅州男子卖自家地获利70万被判刑八个月
     * hasImg : 1
     * digest : 法官:土地属国家或集体所有，个人不得侵占、买卖。
     * hasIcon : false
     * docid : BKHPMJ8600014AEE
     * title : 广东梅州男子卖自家地被判刑
     * order : 1
     * priority : 124
     * lmodify : 2016-04-13 15:51:14
     * boardid : news3_bbs
     * ads : [{"title":"老汉住防空洞20年 称此曾为医院太平间","tag":"photoset","imgsrc":"http://img4.cache.netease.com/3g/2016/4/13/20160413220433fa45c.jpg","subtitle":"","url":"00AP0001|115790"},{"title":"东莞工地坍塌事故已致12人死亡2人失联","tag":"photoset","imgsrc":"http://img1.cache.netease.com/3g/2016/4/13/201604131703450305f.jpg","subtitle":"","url":"00AP0001|115750"},{"title":"河南11人假冒领导诈骗 一年卷走500万","tag":"photoset","imgsrc":"http://img2.cache.netease.com/3g/2016/4/13/2016041316362314938.jpg","subtitle":"","url":"00AP0001|115771"},{"title":"广西3男子深夜盗挖钟乳石 2人被困洞中","tag":"photoset","imgsrc":"http://img2.cache.netease.com/3g/2016/4/13/20160413163934a2514.jpg","subtitle":"","url":"00AP0001|115765"},{"title":"霍金宣布新太空探索计划 寻找外星生物","tag":"photoset","imgsrc":"http://img2.cache.netease.com/3g/2016/4/13/20160413164031ff834.jpg","subtitle":"","url":"00AO0001|115764"}]
     * url_3w : http://news.163.com/16/0413/14/BKHPMJ8600014AEE.html
     * template : manual
     * votecount : 211760
     * alias : Top News
     * cid : C1348646712614
     * url : http://3g.163.com/news/16/0413/14/BKHPMJ8600014AEE.html
     * hasAD : 1
     * source : 金羊网
     * tname : 头条
     * ename : androidnews
     * imgsrc : http://img6.cache.netease.com/3g/2016/4/13/2016041315405815014.jpg
     * subtitle :
     * ptime : 2016-04-13 14:35:44
     */

    private List<T1348647909107Bean> T1348647909107;

    public List<T1348647909107Bean> getT1348647909107() {
        return T1348647909107;
    }

    public void setT1348647909107(List<T1348647909107Bean> T1348647909107) {
        this.T1348647909107 = T1348647909107;
    }

    public static class T1348647909107Bean {
        private String postid;
        private boolean hasCover;
        private int hasHead;
        private int replyCount;
        private String ltitle;
        private int hasImg;
        private String digest;
        private boolean hasIcon;
        private String docid;
        private String title;
        private int order;
        private int priority;
        private String lmodify;
        private String boardid;
        private String url_3w;
        private String template;
        private int votecount;
        private String alias;
        private String cid;
        private String url;
        private int hasAD;
        private String source;
        private String tname;
        private String ename;
        private String imgsrc;
        private String subtitle;
        private String ptime;
        /**
         * title : 老汉住防空洞20年 称此曾为医院太平间
         * tag : photoset
         * imgsrc : http://img4.cache.netease.com/3g/2016/4/13/20160413220433fa45c.jpg
         * subtitle :
         * url : 00AP0001|115790
         */

        private List<AdsBean> ads;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public int getHasHead() {
            return hasHead;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getLtitle() {
            return ltitle;
        }

        public void setLtitle(String ltitle) {
            this.ltitle = ltitle;
        }

        public int getHasImg() {
            return hasImg;
        }

        public void setHasImg(int hasImg) {
            this.hasImg = hasImg;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getLmodify() {
            return lmodify;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public String getUrl_3w() {
            return url_3w;
        }

        public void setUrl_3w(String url_3w) {
            this.url_3w = url_3w;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getVotecount() {
            return votecount;
        }

        public void setVotecount(int votecount) {
            this.votecount = votecount;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHasAD() {
            return hasAD;
        }

        public void setHasAD(int hasAD) {
            this.hasAD = hasAD;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public static class AdsBean {
            private String title;
            private String tag;
            private String imgsrc;
            private String subtitle;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}

