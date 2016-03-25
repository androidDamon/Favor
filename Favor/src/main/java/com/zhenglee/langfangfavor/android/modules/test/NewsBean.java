package com.zhenglee.langfangfavor.android.modules.test;

import java.io.Serializable;
import java.util.List;

/**
 * Description : 新闻实体类
 *
 */
public class NewsBean implements Serializable {
    /**
     * postid : BIESPFKJ00014AEE
     * hasCover : false
     * hasHead : 1
     * replyCount : 144
     * ltitle : 情侣大街上闹分手 男子挽回不成挥拳殴打路人
     * hasImg : 1
     * digest : 男子欲挽回女友不成，借着酒劲一拳打向路人。
     * hasIcon : false
     * docid : BIESPFKJ00014AEE
     * title : 情侣街上闹分手 男友殴打路人
     * order : 1
     * priority : 98
     * lmodify : 2016-03-18 15:10:32
     * boardid : news3_bbs
     * ads : [{"title":"恒大金服重磅上线前景可期","tag":"doc","imgsrc":"http://img4.cache.netease.com/3g/2016/3/18/2016031811342911845.jpg","subtitle":"商讯","url":"BIEN7NR800963VRO"},{"title":"走进沈阳造币厂\u201c见钱最多的人\u201d","tag":"photoset","imgsrc":"http://img5.cache.netease.com/3g/2016/3/18/20160318095016c036c.jpg","subtitle":"","url":"00AP0001|113509"},{"title":"记录芭蕾舞者背后的艰辛 足部变形严重","tag":"photoset","imgsrc":"http://img2.cache.netease.com/3g/2016/3/18/2016031812281223c96.jpg","subtitle":"","url":"00AO0001|113535"},{"title":"学校仅3名学生 残疾教师38年坚守讲台","tag":"photoset","imgsrc":"http://img1.cache.netease.com/3g/2016/3/18/20160318104740536d2.jpg","subtitle":"","url":"00AP0001|113518"},{"title":"父亲寻替身演逝子 圆老母亲见孙儿心愿","tag":"photoset","imgsrc":"http://img2.cache.netease.com/3g/2016/3/18/20160318074626cdca3.jpg","subtitle":"","url":"00AP0001|113491"}]
     * url_3w : http://news.163.com/16/0318/15/BIESPFKJ00014AEE.html
     * template : manual
     * votecount : 100
     * alias : Top News
     * cid : C1348646712614
     * url : http://3g.163.com/news/16/0318/15/BIESPFKJ00014AEE.html
     * hasAD : 1
     * source : 四川新闻网
     * tname : 头条
     * ename : androidnews
     * imgsrc : http://img6.cache.netease.com/3g/2016/1/29/201601291403280586f.jpg
     * subtitle :
     * ptime : 2016-03-18 14:54:27
     */

    private List<T1348647909107Entity> T1348647909107;

    public void setT1348647909107(List<T1348647909107Entity> T1348647909107) {
        this.T1348647909107 = T1348647909107;
    }

    public List<T1348647909107Entity> getT1348647909107() {
        return T1348647909107;
    }

    public static class T1348647909107Entity {
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
         * title : 恒大金服重磅上线前景可期
         * tag : doc
         * imgsrc : http://img4.cache.netease.com/3g/2016/3/18/2016031811342911845.jpg
         * subtitle : 商讯
         * url : BIEN7NR800963VRO
         */

        private List<AdsEntity> ads;

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public void setLtitle(String ltitle) {
            this.ltitle = ltitle;
        }

        public void setHasImg(int hasImg) {
            this.hasImg = hasImg;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public void setUrl_3w(String url_3w) {
            this.url_3w = url_3w;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public void setVotecount(int votecount) {
            this.votecount = votecount;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setHasAD(int hasAD) {
            this.hasAD = hasAD;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public void setAds(List<AdsEntity> ads) {
            this.ads = ads;
        }

        public String getPostid() {
            return postid;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public int getHasHead() {
            return hasHead;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public String getLtitle() {
            return ltitle;
        }

        public int getHasImg() {
            return hasImg;
        }

        public String getDigest() {
            return digest;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public String getDocid() {
            return docid;
        }

        public String getTitle() {
            return title;
        }

        public int getOrder() {
            return order;
        }

        public int getPriority() {
            return priority;
        }

        public String getLmodify() {
            return lmodify;
        }

        public String getBoardid() {
            return boardid;
        }

        public String getUrl_3w() {
            return url_3w;
        }

        public String getTemplate() {
            return template;
        }

        public int getVotecount() {
            return votecount;
        }

        public String getAlias() {
            return alias;
        }

        public String getCid() {
            return cid;
        }

        public String getUrl() {
            return url;
        }

        public int getHasAD() {
            return hasAD;
        }

        public String getSource() {
            return source;
        }

        public String getTname() {
            return tname;
        }

        public String getEname() {
            return ename;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getPtime() {
            return ptime;
        }

        public List<AdsEntity> getAds() {
            return ads;
        }

        public static class AdsEntity {
            private String title;
            private String tag;
            private String imgsrc;
            private String subtitle;
            private String url;

            public void setTitle(String title) {
                this.title = title;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public String getTag() {
                return tag;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public String getUrl() {
                return url;
            }
        }
    }


//    /**
//     * docid
//     */
//    private String docid;
//    /**
//     * 标题
//     */
//    private String title;
//    /**
//     * 小内容
//     */
//    private String digest;
//    /**
//     * 图片地址
//     */
//    private String imgsrc;
//    /**
//     * 来源
//     */
//    private String source;
//    /**
//     * 时间
//     */
//    private String ptime;
//    /**
//     * TAG
//     */
//    private String tag;
//
//    public String getDocid() {
//        return docid;
//    }
//
//    public void setDocid(String docid) {
//        this.docid = docid;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDigest() {
//        return digest;
//    }
//
//    public void setDigest(String digest) {
//        this.digest = digest;
//    }
//
//    public String getImgsrc() {
//        return imgsrc;
//    }
//
//    public void setImgsrc(String imgsrc) {
//        this.imgsrc = imgsrc;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String getPtime() {
//        return ptime;
//    }
//
//    public void setPtime(String ptime) {
//        this.ptime = ptime;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//
//    @Override
//    public String toString() {
//        return new GsonBuilder().create().toJson(this);
//    }
}
