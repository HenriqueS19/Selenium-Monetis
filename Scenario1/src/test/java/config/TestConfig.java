package config;

import io.github.cdimascio.dotenv.Dotenv;

public class TestConfig {

    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .ignoreIfMissing()
            .load();

    public static final String BASE_URL = getEnv("BASE_URL", "https://monetis-delta.vercel.app");
    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String DASHBOARD_URL = BASE_URL + "/dashboard";

    public static final String TEST_USER_EMAIL = getEnvRequired("TEST_USER_EMAIL");
    public static final String TEST_USER_PASSWORD = getEnvRequired("TEST_USER_PASSWORD");
    public static final String NEW_USER_PASSWORD = getEnvRequired("NEW_USER_PASSWORD");

    private static final String API_BASE = BASE_URL + "/api";
    public static final String API_GET_IBAN = API_BASE + "/users/api/getIbanByEmail";
    public static final String API_CREATE_ACCOUNT = API_BASE + "/accounts/api/createAccount";
    public static final String API_REGISTER_USER = API_BASE + "/users/register";
    public static final String API_ADD_MONEY = API_BASE + "/users/api/addMoney";

    public static final int DEFAULT_TIMEOUT = 10;
    public static final int LONG_TIMEOUT = 20;
    public static final int SHORT_TIMEOUT = 5;

    public static final String TEST_ACCOUNT_NAME = "Ibiza";
    public static final String TEST_INITIAL_DEPOSIT = "200";
    public static final int INITIAL_MONEY_AMOUNT = 300;

    public static final String TEST_IBAN = "PT50000201231234567890154";

    private static String getEnv(String key, String defaultValue) {
        String value = dotenv.get(key);
        return value != null ? value : defaultValue;
    }

    private static String getEnvRequired(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Environment variable '" + key + "' is required but not set. Please configure your .env file.");
        }
        return value;
    }
}