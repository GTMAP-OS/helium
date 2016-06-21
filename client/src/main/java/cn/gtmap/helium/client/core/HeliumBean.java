package cn.gtmap.helium.client.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * HeliumConfig 配置参数 Bean 对象.
 * Author: <a href="mailto:yingxiufeng@gtmap.cn">yingxiufeng</a>
 * Date:  2016/6/18 9:01
 */
public class HeliumBean {

    @JsonProperty("helium.config.resources")
    private List<Resource> resources = new ArrayList<Resource>();


    /***
     * 返回配置文件资源集合.
     * 对gtmap的配置
     * @return
     */
    public List<Resource> getResources() {
        return resources;
    }

    /***
     * 设置配置文件资源集合.
     * @param resources
     */
    public void setResources(List<Resource> resources) {
        this.resources.clear();
        this.resources.addAll(resources);
    }
    /**
     * 添加配置文件资源.
     *
     * @param resource 配置文件
     * @return true/false
     */
    public boolean addResource(Resource resource) {
        return resources.add(resource);
    }

    /**
     * 根据配置文件路径添加配置资源.
     * @param location
     * @return
     */
    public boolean addResource(String location) {
        Resource resource = new Resource();
        resource.setLocation(location);
        return addResource(resource);
    }

    /***
     *
     * @param location
     * @param ignoreNotFound   如果资源不存在是否忽略
     * @return
     */
    public boolean addResource(String location, boolean ignoreNotFound) {
        Resource resource = new Resource();
        resource.setLocation(location);
        resource.setIgnoreNotFound(ignoreNotFound);
        return addResource(resource);
    }


    /***
     * resource
     */
    public static class Resource {
        private String location;
        private boolean ignoreNotFound;

        /**
         * 返回资源路径表达式.
         *
         * @return 资源路径表达式
         */
        public String getLocation() {
            return location;
        }

        /**
         * 设置资源路径表达式.
         *
         * @param location 资源路径表达式
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /**
         * 如果资源不存在是否忽略.
         *
         * @return true/false
         */
        public boolean isIgnoreNotFound() {
            return ignoreNotFound;
        }

        /**
         * 如果资源不存在是否忽略
         *
         * @param ignoreNotFound true/false
         */
        public void setIgnoreNotFound(boolean ignoreNotFound) {
            this.ignoreNotFound = ignoreNotFound;
        }

        @Override
        public String toString() {
            return "Resource{" +
                    "location='" + location + '\'' +
                    ", ignoreNotFound=" + ignoreNotFound +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HeliumBean{" +
                "resources=" + resources +
                '}';
    }
}
