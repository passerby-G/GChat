package demo.state;

public class SpeedState extends CourseVideoState {

    public void play() {

        super.courseVideoContext.setCourseVideoState(CourseVideoContext.PLAY_STATE);

    }

    public void speed() {

        System.out.println("快进播放视频");
    }

    public void pause() {
        super.courseVideoContext.setCourseVideoState(CourseVideoContext.PAUSE_STATE);
    }

    public void stop() {
        super.courseVideoContext.setCourseVideoState(CourseVideoContext.STOP_STATE);
    }

}
