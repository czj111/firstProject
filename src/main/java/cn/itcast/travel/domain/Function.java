package cn.itcast.travel.domain;

/**
 * 用户功能
 */
public class Function {
    String funcName;
    String cid;

    public Function() {
    }

    @Override
    public String toString() {
        return "Function{" +
                "funcName='" + funcName + '\'' +
                ", cid=" + cid +
                '}';
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
