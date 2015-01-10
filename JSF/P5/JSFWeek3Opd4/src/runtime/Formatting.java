package runtime;

/**
 *
 * @author jeroen
 */
public class Formatting {

    private enum MetricPrefix {

        Kilo,
        Mega,
        Giga,
        Tera,
        Peta;

        public MetricPrefix next() {
            MetricPrefix prefixes[] = MetricPrefix.values();
            int ordinal = this.ordinal() + 1;
            return prefixes[ordinal];
        }
    }

    private enum ShortMetricPrefix {

        K,
        M,
        G,
        T,
        P;

        public ShortMetricPrefix next() {
            ShortMetricPrefix prefixes[] = ShortMetricPrefix.values();
            int ordinal = this.ordinal() +1;
            return prefixes[ordinal];
        }
    }

    public static String nextShortMetricPrefix(String prefixString) {
        if (prefixString.isEmpty()) {
            return ShortMetricPrefix.K.toString();
        } else {
            ShortMetricPrefix prefix = ShortMetricPrefix.valueOf(prefixString);
            prefix = prefix.next();
            return prefix.toString();
        }
    }

    public static String nextMetricPrefix(String suffix) {
        String returner = null;
        switch (suffix) {
            case "":
                returner = "Kilo";
                break;
            case "Kilo":
                returner = "Mega";
                break;
            case "Mega":
                returner = "Giga";
                break;
            case "Giga":
                returner = "Tera";
                break;
            case "Tera":
                returner = "Peta";
                break;
        }
        return returner;
    }

}
