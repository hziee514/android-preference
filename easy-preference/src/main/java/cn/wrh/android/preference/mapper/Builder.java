package cn.wrh.android.preference.mapper;

/**
 * @author bruce.wu
 * @date 2018/7/4
 */
public class Builder {

    public static Mapper chain() {
        DoubleMapper doubleMapper = new DoubleMapper(null);
        LongMapper longMapper = new LongMapper(doubleMapper);
        IntMapper intMapper = new IntMapper(longMapper);
        BooleanMapper booleanMapper = new BooleanMapper(intMapper);
        return new StringMapper(booleanMapper);
    }

}
