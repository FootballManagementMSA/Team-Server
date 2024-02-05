package sejong.team.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FCMService {
    /**
     * 토큰 기반 전송 -> 특정 사용자의 디바이스에 개별적인 알림을 전송(특정 한명에게 전송)
     * @param fcmToken
     * @param scheduleNotificationDto
     */
    public void sendNotificationToToken(String fcmToken,
                                        ScheduleNotificationDto scheduleNotificationDto) {

        Message message = Message.builder()
                .setNotification(getnotification())
                .setToken(fcmToken)
                .putAllData(getData(scheduleNotificationDto))
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("전송 성공: " + response);
        } catch (Exception e) {
            log.info("전송 실패: " + e);
        }
    }

    /**
     * 토픽 기반 전송 ->  구독자들에게 일반적인 알림을 전송(다수에게 전송)
     * @param topic
     * @param scheduleNotificationDto
     */
    public void sendNotificationToTopic(String topic,
                                        ScheduleNotificationDto scheduleNotificationDto) {

        Message message = Message.builder()
                .setNotification(getnotification())
                .setTopic(topic)
                .putAllData(getData(scheduleNotificationDto))
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("전송 성공: " + response);
        } catch (Exception e) {
            log.info("전송 실패: " + e);
        }
    }

    private Notification getnotification(){

        Notification notification = Notification.builder()
                .setTitle("새로운 일정")
                .setBody("일정이 생성되었습니다")
                .build();

        return notification;
    }

    /**
     * DatetimeFormat 협의 필요.
     * @param scheduleNotificationDto
     * @return
     */
    private Map<String,String> getData(ScheduleNotificationDto scheduleNotificationDto){
        Map<String, String> data = new HashMap<>();
        data.put("title", scheduleNotificationDto.getTitle());
        data.put("memo", scheduleNotificationDto.getMemo());
        data.put("homeTeamName", scheduleNotificationDto.getHomeTeamName());
        data.put("homeTeamImage", scheduleNotificationDto.getHomeTeamEmblem());
        data.put("homeTeamUniqueNumber", scheduleNotificationDto.getHomeTeamUniqueNumber());
        data.put("awayTeamName", scheduleNotificationDto.getAwayTeamName());
        data.put("awayTeamImage", scheduleNotificationDto.getAwayTeamEmblem());
        data.put("awayTeamUniqueNumber", scheduleNotificationDto.getAwayTeamUniqueNumber());
        data.put("startTime", scheduleNotificationDto.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        data.put("endTime", scheduleNotificationDto.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        data.put("place", scheduleNotificationDto.getPlace());
        data.put("longitude", scheduleNotificationDto.getLongitude().toString());
        data.put("latitude", scheduleNotificationDto.getLatitude().toString());

        return data;
    }
}
