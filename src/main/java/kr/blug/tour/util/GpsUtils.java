package kr.blug.tour.util;

public class GpsUtils {

    public static GpsRange calculateGpsRange(String pointX, String pointY, double radiusKm) {
        try {
            double px = Double.parseDouble(pointX);
            double py = Double.parseDouble(pointY);

            double latDelta = radiusKm / 111.0;
            double lonDelta = radiusKm / (111.0 * Math.cos(Math.toRadians(py)));

            String pxMin = formatGps(px - lonDelta);
            String pxMax = formatGps(px + lonDelta);
            String pyMin = formatGps(py - latDelta);
            String pyMax = formatGps(py + latDelta);

            return new GpsRange(pxMin, pxMax, pyMin, pyMax);

        } catch (NumberFormatException e) {
            System.out.println("Invalid GPS coordinate format: " + pointX + ", " + pointY);
            return null;
        }
    }

    private static String formatGps(double value) {
        return String.format("%.6f", value);
    }

    public static class GpsRange {
        public final String pxMin;
        public final String pxMax;
        public final String pyMin;
        public final String pyMax;

        public GpsRange(String pxMin, String pxMax, String pyMin, String pyMax) {
            this.pxMin = pxMin;
            this.pxMax = pxMax;
            this.pyMin = pyMin;
            this.pyMax = pyMax;
        }
    }
}