package com.example.stocker.toolsOpe;

import android.text.format.DateUtils;
import android.util.Log;

import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.AxisLine;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.SplitArea;
import com.github.abel533.echarts.axis.SplitLine;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Position;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.KData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.feature.Mark;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Candlestick;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.TextStyle;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EchartOptionUtil {

    //[配置折线图
    public static GsonOption getLineChartOptions(Object[] xAxis, Object[] yAxis) {

        GsonOption option = new GsonOption();
        option.title("折线图");//折线图的标题
        option.legend("销量");//添加图例
        option.tooltip().trigger(Trigger.axis);//提示框（详见tooltip），鼠标悬浮交互时的信息提示

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);//添加y轴

        CategoryAxis cateAxis = new CategoryAxis();
        cateAxis.axisLine().onZero(false);//坐标轴线，默认显示，属性show控制显示与否，属性lineStyle（详见lineStyle）控制线条样式
        cateAxis.boundaryGap(true);
        cateAxis.data(xAxis);//添加坐标轴的类目属性
        option.xAxis(cateAxis);//x轴为类目轴
//        设置背景色
        option.backgroundColor("#80766e");
//        设置曲线颜色
        option.color("#4b8db9");
        Line line = new Line();
        //设置折线的相关属性
        line.smooth(true).name("销量").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
        return option;
    }


    public static GsonOption getBarChartOptions(ArrayList<Map<String, Object>> listMaps) {
//        获取传递得到的键值对集合
        GsonOption option = new GsonOption();
        Bar bar = new Bar();
        Mark average = new Mark();//创建标记

        option.title("股市分析");
        option.legend("数量");
        option.tooltip().trigger(Trigger.axis);
//        分别定义y轴，x轴
        ValueAxis valueAxis = new ValueAxis();
        CategoryAxis cateAxis = new CategoryAxis();
//        初始化数据
        Log.d("getSize", String.valueOf(listMaps.size()));
        for (Map<String, Object> objectMap : listMaps) {
            cateAxis.data(objectMap.get("date"));
            bar.data(objectMap.get("money"));
        }
//        cateAxis.data(xAxis);//设置x轴的类目属性
        option.yAxis(valueAxis);//添加y轴，将y轴设置为值轴
        option.xAxis(cateAxis);//添加x轴

//        设置图表背景色
        option.backgroundColor("#8de992");
//        设置曲线颜色
        option.color("#f4cb89");
//        重点在于下面的类调用，根据图表类型找对应接口

        average.type("average").title("平均值");
        bar.markLine().data(average);//标记线
        //滑块 还可以设置startValue和endValue
        option.dataZoom(new DataZoom().type(DataZoomType.slider));
        //拖动
        option.dataZoom(new DataZoom().type(DataZoomType.inside));
        bar.name("销量").itemStyle().normal().setBarBorderColor("rgba(0,0,0,0.4)");
        option.series(bar);
        return option;
    }

    //    配置柱状图,这里用的是对象集合
    public static GsonOption getBarChartOptions(Object[] xAxis, Object[] yAxis) {
//        x是类目轴，y是涨跌幅即值轴
        GsonOption option = new GsonOption();
        Bar bar = new Bar();
        Mark average = new Mark();//创建标记
        AxisLabel label = new AxisLabel();
        TextStyle t = new TextStyle();
        t.setColor("#4b8db9");
        t.setFontSize(10);
        label.setTextStyle(t);

        option.title("领涨排行").tooltip(Trigger.axis).legend("涨跌幅");
        //横轴为值轴
        option.xAxis(new ValueAxis().boundaryGap(0d, 0.01));
        //创建类目轴
        CategoryAxis category = new CategoryAxis();
        category.data(xAxis);
//        设置轴值字体大小
        category.setAxisLabel(label);

        option.yAxis(category);

        //        设置图表背景色
        option.backgroundColor("#FFFF");
//        设置图表填充颜色
        option.color("#d9042b");
        option.tooltip().show(true).formatter("{a} <br/>{b} : {c}");// 显示工具提示,设置提示格式

        option.grid(new Grid().left("20%").right("1%").bottom("20%"));
        //滑块 还可以设置startValue和endValue
        option.dataZoom(new DataZoom().type(DataZoomType.slider));
        //拖动
        option.dataZoom(new DataZoom().type(DataZoomType.inside));
        bar.name("涨跌幅").data(yAxis).itemStyle().normal().setBarBorderColor("rgba(0,0,0,0.4)");
        option.series(bar);
        return option;
    }

    public static GsonOption getKChartOption() {
        ArrayList<Double> yDatas1 = new ArrayList<>();
        ArrayList<Double> yDatas2 = new ArrayList<>();
        ArrayList<Double> yDatas3 = new ArrayList<>();
        ArrayList<Double> yDatas4 = new ArrayList<>();

        yDatas1.add(10.0);
        yDatas1.add(50.0);
        yDatas1.add(120.0);
        yDatas1.add(30.0);
        yDatas2.add(10.0);
        yDatas2.add(50.0);
        yDatas2.add(120.0);
        yDatas2.add(30.0);
        yDatas3.add(10.0);
        yDatas3.add(50.0);
        yDatas3.add(120.0);
        yDatas3.add(30.0);
        yDatas4.add(10.0);
        yDatas4.add(50.0);
        yDatas4.add(120.0);
        yDatas4.add(30.0);


        GsonOption option = new GsonOption();

        Title title = new Title();
        title.text("股票K线图").setTextAlign(X.left);
        option.title(title);

        /*
         * K线图 KData类:为k线图的数据格式设置
         * titleName:标题名称
         * legName-4:分布对应legend五个分类图表
         * vDatas：为x轴名称集合
         * yDatas1-4：对应k线的开盘收盘最低最高价数据集合
         */

        ArrayList<String> vDatas = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            vDatas.add(String.valueOf(i+ 1));
        }

        ArrayList<String> legNames = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            legNames.add("Name" + i);
        }


//        分类图标
        option.legend().data(legNames.get(0), legNames.get(1), legNames.get(2), legNames.get(3), legNames.get(4)).itemHeight(10).itemWidth(10).orient(Orient.vertical)
                .textStyle(new TextStyle().fontSize(10)).right(1).top(30);

        option.tooltip(new Tooltip().position(Position.right).transitionDuration(5.0).trigger(Trigger.axis)
                .axisPointer(new AxisPointer().type(PointerType.cross)).position(Position.top)
                .transitionDuration(500.0));

        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true),
                Tool.restore, Tool.saveAsImage);

        option.calculable(true);
//        option.grid(new Grid().left("10%").right("10%").bottom("15%"));

        CategoryAxis valueAxis = new CategoryAxis();
//        设置横纵坐标，设置提示
        valueAxis.type(AxisType.category).scale(true).boundaryGap(false).axisLine(new AxisLine().onZero(false))
                .splitLine(new SplitLine().show(false)).min("dataMin").max("dataMax");


        for (int i = 0; i < vDatas.size(); i++) {
//            设置x轴不同时间周期
            valueAxis.data(vDatas.get(i));
        }

//        获取时间x轴列表
        option.xAxis(valueAxis);

//        缩放
        option.dataZoom(new DataZoom().type(DataZoomType.inside),
                new DataZoom().show(true).type(DataZoomType.slider).y("90%"));

        option.yAxis(new ValueAxis().scale(true).splitArea(new SplitArea().show(true)));

        Candlestick candlestick = new Candlestick(legNames.get(0));

        //k线图数据赋值，最好有四组数据，分别为 ：第一个数值：开盘 第二个数值：收盘 第三个数值：最低值 第四个数值：最高值
        if (yDatas1.size() > 0 && yDatas2.size() > 0 && yDatas3.size() > 0 && yDatas4.size() > 0) {
            for (int i = 0; i < yDatas1.size(); i++) {
                //为null与""做默认数据添加，防止出现异常
                if (yDatas1.get(i) != null && yDatas2.get(i) != null && yDatas3.get(i) != null && yDatas4.get(i) != null) {
                    if (!yDatas1.get(i).equals("") && !yDatas2.get(i).equals("") && !yDatas3.get(i).equals("") && !yDatas4.get(i).equals("")) {
                        candlestick.data(new KData(Double.valueOf(yDatas1.get(i).toString()), Double.valueOf(yDatas2.get(i).toString())
                                , Double.valueOf(yDatas3.get(i).toString()), Double.valueOf(yDatas4.get(i).toString())));
                    } else {
                        candlestick.data(new KData(0.0, 0.0, 0.0, 0.0));
                    }
                }
            }
        } else {
            candlestick.data(new KData(0.0, 0.0, 0.0, 0.0));
        }


        Line line = new Line(legNames.get(1));
        for (int i = 0; i < yDatas1.size(); i++) {
            line.smooth(true).data(yDatas1.get(i));
        }
        Line line2 = new Line(legNames.get(2));
        for (int i = 0; i < yDatas2.size(); i++) {
            line2.smooth(true).data(yDatas2.get(i));
        }
        Line line3 = new Line(legNames.get(3));
        for (int i = 0; i < yDatas3.size(); i++) {
            line3.smooth(true).data(yDatas3.get(i));
        }
        Line line4 = new Line(legNames.get(4));
        for (int i = 0; i < yDatas4.size(); i++) {
            line4.smooth(true).data(yDatas4.get(i));
        }
        option.series(candlestick, line, line2, line3, line4);

        return option;
    }

    private static int random() {
        return (int) Math.round(Math.random() * 100);
    }

    /*
     * 临时数据 第一个数值：开盘 第二个数值：收盘 第三个数值：最低值 第四个数值：最高值
     */
    private static KData[] randomDataArray() {
        KData[] scatters = new KData[4];
        for (int i = 0; i < scatters.length; i++) {
            scatters[i] = new KData((double) random(), (double) random(), (double) random(),
                    (double) random());
        }
        return scatters;
    }
}