package com.example.bermuda.utils;

import com.example.bermuda.dto.LocationDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeometryUtils {
    public static LocationDto.LocationCoordinateDto calculateByDirection(Double baseLatitude, Double baseLongitude, double distance,
                                                                         Double bearing) {

        Double radianLatitude = toRadian(baseLatitude);
        Double radianLongitude = toRadian(baseLongitude);
        Double radianAngle = toRadian(bearing);
        Double distanceRadius = distance / 6371.01;

        Double latitude = Math.asin(sin(radianLatitude) * cos(distanceRadius) +
                cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
        Double longitude = radianLongitude + Math.atan2(sin(radianAngle) * sin(distanceRadius) * cos(radianLatitude),
                cos(distanceRadius) - sin(radianLatitude) * sin(latitude));

        longitude = (longitude + 540) % 360 - 180;

        return new LocationDto.LocationCoordinateDto(toDegree(latitude), toDegree(longitude));
    }

    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    private static Double sin(Double coordinate) {
        return Math.sin(coordinate);
    }

    private static Double cos(Double coordinate) {
        return Math.cos(coordinate);
    }

    public static Point getEmptyPoint() throws ParseException {
        Double latitude = 0.0;
        Double longitude = 0.0;
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        return (Point)new WKTReader().read(pointWKT);
    }

    //위도 경도 기반으로 point 생성
    public static Point create(double lat, double lon){
        GeometryFactory gf = new GeometryFactory();
        return gf.createPoint(new Coordinate(lat, lon));
    }
}
