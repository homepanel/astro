package com.homepanel.astro.service;

import com.homepanel.astro.astro.model.Range;
import com.homepanel.astro.config.Config;
import com.homepanel.astro.config.Topic;
import com.homepanel.core.executor.PriorityThreadPoolExecutor;
import com.homepanel.core.service.PollingService;
import com.homepanel.core.state.Type;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.quartz.CronScheduleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Service extends PollingService<Config, Topic> {

    private final static Logger LOGGER = LoggerFactory.getLogger(Service.class);

    private Astro astro;

    private Astro getAstro() {
        return astro;
    }

    private void setAstro(Astro astro) {
        this.astro = astro;
    }

    @Override
    public Config getConfig() {
        return (Config) super.getConfig();
    }

    @Override
    protected List<String> getMqttTopics() {
        return new ArrayList<>();
    }

    @Override
    public void start(String[] arguments, Class configClass) {

        super.start(arguments, configClass);

        try {
            startService();
        } catch (Exception e) {
            LOGGER.error("global exception when starting service", e);
        }
    }

    @Override
    public String getTopicNameByTopic(Topic topic) {
        return null;
    }

    @Override
    protected Integer getPollingExecutorServicePoolSize() {
        return 3;
    }

    @Override
    protected void startService() throws Exception {

        if (getConfig().getTopics() != null) {

            setAstro(new Astro(this));

            // set type
            for (Topic topic : getConfig().getTopics()) {

                switch (topic.getPlanet()) {
                    case SUN:
                        switch (topic.getGroup()) {
                            case RISE:
                            case SET:
                            case NOON:
                            case NIGHT:
                            case MORNING_NIGHT:
                            case ASTRO_DAWN:
                            case NAUTIC_DAWN:
                            case CLIVIL_DAWN:
                            case ASTRO_DUSK:
                            case NAUTIC_DUSK:
                            case CILIVL_DUSK:
                            case EVENING_NIGHT:
                            case DAY_LIGHT:
                                topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                break;
                            case POSITION:
                                switch (topic.getChannel()) {
                                    case AZIMUTH:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                    case EVELATION:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                    case SHADE_LENGTH:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                }
                                break;
                            case RADIATION:
                                switch (topic.getChannel()) {
                                    case DIRECT:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                    case DIFFUSE:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                    case TOTAL:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                }
                                break;
                            case ZODIAC:
                                switch (topic.getChannel()) {
                                    case START:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case END:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case SIGN:
                                        topic.setType(Config.getType(Type.NAME.STRING));
                                        break;
                                }
                                break;
                            case SEASON:
                                switch (topic.getChannel()) {
                                    case SPRING:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case SUMMER:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case AUTUMN:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case WINTER:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case NAME:
                                        topic.setType(Config.getType(Type.NAME.STRING));
                                        break;
                                }
                                break;
                            case ECLIPSE:
                                switch (topic.getChannel()) {
                                    case TOTAL:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case PARTIAL:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case RING:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                }
                                break;
                            case PHASE:
                                switch (topic.getChannel()) {
                                    case NAME:
                                        topic.setType(Config.getType(Type.NAME.STRING));
                                        break;
                                }
                                break;
                        }

                        break;

                    case MOON:
                        switch (topic.getGroup()) {
                            case RISE:
                            case SET:
                                topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                break;
                            case PHASE:
                                switch (topic.getChannel()) {
                                    case FIRST_QUATER:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case THIRD_QUATER:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case FULL:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case NEW:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case AGE:
                                        topic.setType(Config.getType(Type.NAME.INTEGER));
                                        break;
                                    case AGE_DEGREE:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                    case AGE_PERCENT:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                    case ILLUMINATION:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                    case NAME:
                                        topic.setType(Config.getType(Type.NAME.STRING));
                                        break;
                                }
                                break;
                            case ECLIPSE:
                                switch (topic.getChannel()) {
                                    case TOTAL:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case PARTIAL:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                }
                                break;
                            case DISTANCE:
                                switch (topic.getChannel()) {
                                    case DATE:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case DISTANCE:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                }
                                break;
                            case PERIGEE:
                                switch (topic.getChannel()) {
                                    case DATE:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case DISTANCE:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                }
                                break;
                            case APOGEE:
                                switch (topic.getChannel()) {
                                    case DATE:
                                        topic.setType(Config.getType(Type.NAME.DATE_TIME));
                                        break;
                                    case DISTANCE:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                }
                                break;
                            case ZODIAC:
                                switch (topic.getChannel()) {
                                    case SIGN:
                                        topic.setType(Config.getType(Type.NAME.STRING));
                                        break;
                                }
                                break;
                            case POSITION:
                                switch (topic.getChannel()) {
                                    case AZIMUTH:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                    case EVELATION:
                                        topic.setType(Config.getType(Type.NAME.DOUBLE));
                                        break;
                                }
                                break;
                        }

                        break;
                }

                if (topic.getType() == null) {
                    LOGGER.error("topic with planet \"{}\", group \"{}\" and channel \"{}\" not found", topic.getPlanet(), topic.getGroup(), topic.getChannel());
                }
            }
        }
    }

    @Override
    protected void shutdownService() throws Exception {
    }

    @Override
    protected List<CronScheduleBuilder> getCronScheduleBuilders() {
        return Arrays.asList(CronScheduleBuilder.cronSchedule("0 0 0 * * ? *"));
    }

    @Override
    protected void onInit() {

        // clean old value and timestamp for init
        for (Topic topic : getConfig().getTopics()) {
            topic.setLastValue(null);
            topic.setLastDateTime(null);
        }

        long jobRunningTimeInMilliseconds = ZonedDateTime.now().toInstant().toEpochMilli();
        long refreshIntervalInMilliseconds = TimeUnit.HOURS.toMillis(1);

        for (Topic topic : getConfig().getTopics()) {
            pollData(topic, jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds);
        }
    }

    @Override
    protected void onData(String topic, String value) {
        // unused
    }

    @Override
    protected void onData(Topic topic, Object value, PriorityThreadPoolExecutor.PRIORITY priority) {
        // unused
    }

    @Override
    protected void updateData(Topic topic) {
        // unused
    }

    @Override
    public void pollData(Topic topic, Long jobRunningTimeInMilliseconds, Long refreshIntervalInMilliseconds) {

        Object value = null;
        Range range = null;

        switch (topic.getPlanet()) {
            case SUN:
                switch (topic.getGroup()) {
                    case RISE:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getRise();
                        break;
                    case SET:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getSet();
                        break;
                    case NOON:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getNoon();
                        break;
                    case NIGHT:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getNight();
                        break;
                    case MORNING_NIGHT:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getMorningNight();
                        break;
                    case ASTRO_DAWN:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getAstroDawn();
                        break;
                    case NAUTIC_DAWN:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getNauticDawn();
                        break;
                    case CLIVIL_DAWN:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getCivilDawn();
                        break;
                    case ASTRO_DUSK:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getAstroDusk();
                        break;
                    case NAUTIC_DUSK:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getNauticDusk();
                        break;
                    case CILIVL_DUSK:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getCivilDusk();
                        break;
                    case EVENING_NIGHT:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getEveningNight();
                        break;
                    case DAY_LIGHT:
                        range = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getDaylight();
                        break;
                    case POSITION:
                        switch (topic.getChannel()) {
                            case AZIMUTH:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPosition().getAzimuth();
                                break;
                            case EVELATION:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPosition().getElevation();
                                break;
                            case SHADE_LENGTH:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPosition().getShadeLength();
                                break;
                        }
                        break;
                    case RADIATION:
                        switch (topic.getChannel()) {
                            case DIRECT:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getRadiation().getDirect();
                                break;
                            case DIFFUSE:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getRadiation().getDiffuse();
                                break;
                            case TOTAL:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getRadiation().getTotal();
                                break;
                        }
                        break;
                    case ZODIAC:
                        switch (topic.getChannel()) {
                            case START:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getZodiac().getStart();
                                break;
                            case END:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getZodiac().getEnd();
                                break;
                            case SIGN:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getZodiac().getSign().name();
                                break;
                        }
                        break;
                    case SEASON:
                        switch (topic.getChannel()) {
                            case SPRING:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getSeason().getSpring();
                                break;
                            case SUMMER:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getSeason().getSummer();
                                break;
                            case AUTUMN:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getSeason().getAutumn();
                                break;
                            case WINTER:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getSeason().getWinter();
                                break;
                            case NAME:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getSeason().getName().name();
                                break;
                        }
                        break;
                    case ECLIPSE:
                        switch (topic.getChannel()) {
                            case TOTAL:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getEclipse().getTotal();
                                break;
                            case PARTIAL:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getEclipse().getPartial();
                                break;
                            case RING:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getEclipse().getRing();
                                break;
                        }
                        break;
                    case PHASE:
                        switch (topic.getChannel()) {
                            case NAME:
                                value = getAstro().getSun(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getName().name();
                                break;
                        }
                        break;
                }

                break;

            case MOON:
                switch (topic.getGroup()) {
                    case RISE:
                        range = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getRise();
                        break;
                    case SET:
                        range = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getSet();
                        break;
                    case PHASE:
                        switch (topic.getChannel()) {
                            case FIRST_QUATER:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getFirstQuarter();
                                break;
                            case THIRD_QUATER:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getThirdQuarter();
                                break;
                            case FULL:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getFull();
                                break;
                            case NEW:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getNew();
                                break;
                            case AGE:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getAge();
                                break;
                            case AGE_DEGREE:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getAgeDegree();
                                break;
                            case AGE_PERCENT:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getAgePercent();
                                break;
                            case ILLUMINATION:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getIllumination();
                                break;
                            case NAME:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPhase().getName().name();
                                break;
                        }
                        break;
                    case ECLIPSE:
                        switch (topic.getChannel()) {
                            case TOTAL:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getEclipse().getTotal();
                                break;
                            case PARTIAL:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getEclipse().getPartial();
                                break;
                        }
                        break;
                    case DISTANCE:
                        switch (topic.getChannel()) {
                            case DATE:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getDistance().getDate();
                                break;
                            case DISTANCE:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getDistance().getDistance();
                                break;
                        }
                        break;
                    case PERIGEE:
                        switch (topic.getChannel()) {
                            case DATE:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPerigee().getDate();
                                break;
                            case DISTANCE:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPerigee().getDistance();
                                break;
                        }
                        break;
                    case APOGEE:
                        switch (topic.getChannel()) {
                            case DATE:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getApogee().getDate();
                                break;
                            case DISTANCE:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getApogee().getDistance();
                                break;
                        }
                        break;
                    case ZODIAC:
                        switch (topic.getChannel()) {
                            case SIGN:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getZodiac().getSign().name();
                                break;
                        }
                        break;
                    case POSITION:
                        switch (topic.getChannel()) {
                            case AZIMUTH:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPosition().getAzimuth();
                                break;
                            case EVELATION:
                                value = getAstro().getMoon(jobRunningTimeInMilliseconds, refreshIntervalInMilliseconds).getPosition().getElevation();
                                break;
                        }
                        break;
                }

                break;
        }

        if (range != null) {
            switch (topic.getChannel()) {
                case START:
                    value = range.getStart();
                    break;
                case END:
                    value = range.getEnd();
                    break;
            }
        }

        if (topic.getLastDateTime() == null || !new EqualsBuilder().append(topic.getLastValue(), value).isEquals()) {
            topic.setLastValue(value);
            topic.setLastDateTime(LocalDateTime.now());

            publishData(topic.getPath(), topic.getType().convertObjectToString(value));
        }
    }

    public static void main(String[] arguments) throws Exception {
        new Service().start(arguments, Config.class);
    }
}