package demo.state;

public class StopState extends CourseVideoState {

    public void play() {

        super.courseVideoContext.setCourseVideoState(CourseVideoContext.PLAY_STATE);

    }

    public void speed() {

        System.out.println("ERROR 停止状态不能快进");
    }

    public void pause() {
        System.out.println("ERROR 停止状态不能暂停");
    }

    public void stop() {
        System.out.println("停止播放视频");
    }



}
