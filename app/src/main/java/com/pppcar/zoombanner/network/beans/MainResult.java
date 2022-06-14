package com.pppcar.zoombanner.network.beans;

import java.util.List;

/**
 * Author  ： logan
 * Time    ： 2022/5/19 6:44 下午
 * Desc    ：
 */
public class MainResult {

    private PageResRemonmendProDTO pageResRemonmendPro;
    private List<ResIndexBannersDTO> resIndexBanners;
    private List<ResIndexMenusDTO> resIndexMenus;
    private List<ResIndexRecommendTypesDTO> resIndexRecommendTypes;
    private List<ResIndexBrandTypesDTO> resIndexBrandTypes;
    private List<NewsDTO> news;

    public PageResRemonmendProDTO getPageResRemonmendPro() {
        return pageResRemonmendPro;
    }

    public void setPageResRemonmendPro(PageResRemonmendProDTO pageResRemonmendPro) {
        this.pageResRemonmendPro = pageResRemonmendPro;
    }

    public List<ResIndexBannersDTO> getResIndexBanners() {
        return resIndexBanners;
    }

    public void setResIndexBanners(List<ResIndexBannersDTO> resIndexBanners) {
        this.resIndexBanners = resIndexBanners;
    }

    public List<ResIndexMenusDTO> getResIndexMenus() {
        return resIndexMenus;
    }

    public void setResIndexMenus(List<ResIndexMenusDTO> resIndexMenus) {
        this.resIndexMenus = resIndexMenus;
    }

    public List<ResIndexRecommendTypesDTO> getResIndexRecommendTypes() {
        return resIndexRecommendTypes;
    }

    public void setResIndexRecommendTypes(List<ResIndexRecommendTypesDTO> resIndexRecommendTypes) {
        this.resIndexRecommendTypes = resIndexRecommendTypes;
    }

    public List<ResIndexBrandTypesDTO> getResIndexBrandTypes() {
        return resIndexBrandTypes;
    }

    public void setResIndexBrandTypes(List<ResIndexBrandTypesDTO> resIndexBrandTypes) {
        this.resIndexBrandTypes = resIndexBrandTypes;
    }

    public List<NewsDTO> getNews() {
        return news;
    }

    public void setNews(List<NewsDTO> news) {
        this.news = news;
    }

    public static class PageResRemonmendProDTO {
        private int page;
        private int size;
        private int totalPage;
        private int totalSize;
        private int nextPage;
        private List<ResRemonmendProsDTO> resRemonmendPros;
        private List<ResRemonmendSpusDTO> resRemonmendSpus;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public List<ResRemonmendProsDTO> getResRemonmendPros() {
            return resRemonmendPros;
        }

        public void setResRemonmendPros(List<ResRemonmendProsDTO> resRemonmendPros) {
            this.resRemonmendPros = resRemonmendPros;
        }

        public List<ResRemonmendSpusDTO> getResRemonmendSpus() {
            return resRemonmendSpus;
        }

        public void setResRemonmendSpus(List<ResRemonmendSpusDTO> resRemonmendSpus) {
            this.resRemonmendSpus = resRemonmendSpus;
        }

        public static class ResRemonmendProsDTO {
            private int id;
            private String imgs;
            private String name;
            private double retailPrice;
            private String actIcon;
            private boolean actOpen;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(double retailPrice) {
                this.retailPrice = retailPrice;
            }

            public String getActIcon() {
                return actIcon;
            }

            public void setActIcon(String actIcon) {
                this.actIcon = actIcon;
            }

            public boolean isActOpen() {
                return actOpen;
            }

            public void setActOpen(boolean actOpen) {
                this.actOpen = actOpen;
            }
        }

        public static class ResRemonmendSpusDTO {
            private int id;
            private String imgs;
            private String name;
            private double retailPrice;
            private String actIcon;
            private boolean actOpen;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getRetailPrice() {
                return retailPrice;
            }

            public void setRetailPrice(double retailPrice) {
                this.retailPrice = retailPrice;
            }

            public String getActIcon() {
                return actIcon;
            }

            public void setActIcon(String actIcon) {
                this.actIcon = actIcon;
            }

            public boolean isActOpen() {
                return actOpen;
            }

            public void setActOpen(boolean actOpen) {
                this.actOpen = actOpen;
            }
        }
    }

    public static class ResIndexBannersDTO {
        private String title;
        private String imgUrl;
        private String imgLinkUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgLinkUrl() {
            return imgLinkUrl;
        }

        public void setImgLinkUrl(String imgLinkUrl) {
            this.imgLinkUrl = imgLinkUrl;
        }
    }

    public static class ResIndexMenusDTO {
        private String menuName;
        private String imgUrl;
        private String imgLinkUrl;

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgLinkUrl() {
            return imgLinkUrl;
        }

        public void setImgLinkUrl(String imgLinkUrl) {
            this.imgLinkUrl = imgLinkUrl;
        }
    }

    public static class ResIndexRecommendTypesDTO {
        private int isLarge;
        private String imgUrl;
        private String imgLinkUrl;

        public int getIsLarge() {
            return isLarge;
        }

        public void setIsLarge(int isLarge) {
            this.isLarge = isLarge;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgLinkUrl() {
            return imgLinkUrl;
        }

        public void setImgLinkUrl(String imgLinkUrl) {
            this.imgLinkUrl = imgLinkUrl;
        }
    }

    public static class ResIndexBrandTypesDTO {
        private String title;
        private String imgUrl;
        private String imgLinkUrl;
        private List<IndexBrandListsDTO> indexBrandLists;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgLinkUrl() {
            return imgLinkUrl;
        }

        public void setImgLinkUrl(String imgLinkUrl) {
            this.imgLinkUrl = imgLinkUrl;
        }

        public List<IndexBrandListsDTO> getIndexBrandLists() {
            return indexBrandLists;
        }

        public void setIndexBrandLists(List<IndexBrandListsDTO> indexBrandLists) {
            this.indexBrandLists = indexBrandLists;
        }

        public static class IndexBrandListsDTO {
            private String brandImg;
            private String brandLinkUrl;
            private int displayOrder;
            private int isShow;
            private int isLarge;

            public String getBrandImg() {
                return brandImg;
            }

            public void setBrandImg(String brandImg) {
                this.brandImg = brandImg;
            }

            public String getBrandLinkUrl() {
                return brandLinkUrl;
            }

            public void setBrandLinkUrl(String brandLinkUrl) {
                this.brandLinkUrl = brandLinkUrl;
            }

            public int getDisplayOrder() {
                return displayOrder;
            }

            public void setDisplayOrder(int displayOrder) {
                this.displayOrder = displayOrder;
            }

            public int getIsShow() {
                return isShow;
            }

            public void setIsShow(int isShow) {
                this.isShow = isShow;
            }

            public int getIsLarge() {
                return isLarge;
            }

            public void setIsLarge(int isLarge) {
                this.isLarge = isLarge;
            }
        }
    }

    public static class NewsDTO {
        private int id;
        private String title;
        private String img;
        private long createTime;
        private String userName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
