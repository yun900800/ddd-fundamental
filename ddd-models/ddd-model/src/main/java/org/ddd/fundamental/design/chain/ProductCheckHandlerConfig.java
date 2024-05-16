package org.ddd.fundamental.design.chain;

public class ProductCheckHandlerConfig {

    /**
     * 处理器Bean名称
     */
    private String handler;
    /**
     * 下一个处理器
     */
    private ProductCheckHandlerConfig next;
    /**
     * 是否降级
     */
    private Boolean down = Boolean.FALSE;

    public ProductCheckHandlerConfig(String handler,
                                     ProductCheckHandlerConfig next,
                                     boolean down){
        this.down = down;
        this.handler = handler;
        this.next = next;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public ProductCheckHandlerConfig getNext() {
        return next;
    }

    public void setNext(ProductCheckHandlerConfig next) {
        this.next = next;
    }

    public Boolean getDown() {
        return down;
    }

    public void setDown(Boolean down) {
        this.down = down;
    }
}
