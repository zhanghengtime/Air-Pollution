package com.example.tianqi.utils;

public class Air{
    public int id; //id
    public String Name; //站点名称
    public String King; //设备类型
    public String uKing; //源类型
    public String uPlace; //区县
    public float Jingdu; //经度
    public float Weidu; //纬度
    public String uTime; //时间
    public int AQI; //AQI
    public int PM10; //PM10
    public int PM25; //PM25
    public int SO2; //SO2
    public int NO2; //NO2
    public int O3; //O3
    public int CO; //CO
    public String pollu; //首要污染物
    public float Wendu; //温度
    public float Shidu; //湿度
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getKind() {
        return King;
    }
    public void setKind(String Kind) {
        this.King = Kind;
    }
    public String getuKing(){
        return uKing;
    }
    public void setuKing(String uKing){
        this.uKing = uKing;
    }
    public String getuPlace(){
        return uPlace;
    }
    public void setuPlace(String uPlace){
        this.uPlace = uPlace;
    }
    public float getJingdu(){
        return Jingdu;
    }
    public void setJingdu(float Jingdu){
        this.Jingdu = Jingdu;
    }
    public float getWeidu(){
        return Weidu;
    }
    public void setWeidu(float Weidu){
        this.Weidu = Weidu;
    }
    public String getuTime(){
        return uTime;
    }
    public void setuTime(String uTime){
        this.uTime = uTime;
    }
    public int getAQI(){
        return AQI;
    }
    public void setAQI(int AQI){
        this.AQI = AQI;
    }
    public int getPM10(){
        return PM10;
    }
    public void setPM10(int PM10){
        this.PM10 = PM10;
    }
    public int getPM25(){
        return PM25;
    }
    public void setPM25(int PM25){
        this.PM25 = PM25;
    }
    public int getSO2(){
        return SO2;
    }
    public void setSO2(int SO2){
        this.SO2 = SO2;
    }
    public int getNO2(){
        return NO2;
    }
    public void setNO2(int NO2){
        this.NO2 = NO2;
    }
    public int getO3(){
        return O3;
    }
    public void setO3(int O3){
        this.O3 = O3;
    }
    public int getCO(){
        return CO;
    }
    public void setCO(int CO){
        this.CO = CO;
    }
    public String getPollu(){
        return pollu;
    }
    public void setPollu(String pollu){
        this.pollu = pollu;
    }
    public float getWendu(){
        return Wendu;
    }
    public void setWendu(float Wendu){
        this.Weidu = Wendu;
    }
    public float getShidu(){
        return Shidu;
    }
    public void setShidu(float Shidu){
        this.Shidu = Shidu;
    }

    public String toString(){
        String result = "";
        result += "站点名称：" + this.Name+ "， ";
        result += "设备类型：" + this.King + "， ";
        result += "源类型：" + this.uKing + "， ";
        result += "区县: " + this.uPlace + ", ";
        result += "经度: " + this.Jingdu + ", ";
        result += "纬度:" + this.Weidu + ", ";
        result += "时间: " + this.uTime + ", ";
        result += "AQI: " + this.AQI + ", ";
        result += "PM10: " + this.PM10 + ", ";
        result += "PM25: " + this.PM25 + ", ";
        result += "SO2: " + this.SO2 + ", ";
        result += "NO2: " + this.NO2 + ", ";
        result += "O3: " + this.O3 + ", ";
        result += "CO: " + this.CO + ", ";
        result += "首要污染物: " + this.pollu + ", ";
        result += "温度: " + this.Wendu + ", ";
        result += "湿度: " + this.Shidu + ", ";
        return result;
    }
}
