package com.cool.jordan.asteroidalarm;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import static com.cool.jordan.asteroidalarm.AsteroidApp.DAILY_ASTEROID;
import static com.cool.jordan.asteroidalarm.AsteroidApp.DANGEROUS_ASTEROID;

public class AsteroidJobService extends JobService {

    public AsteroidJobService() {
        super();
    }

    public static void initializeDangerousCheck(Application app) {
        JobScheduler jobScheduler = (JobScheduler) app.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName name = new ComponentName(app, AsteroidJobService.class);
        JobInfo info = new JobInfo.Builder(DANGEROUS_ASTEROID, name)
                .setPeriodic(TimeUnit.DAYS.toMillis(1))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setPersisted(true)
                .build();
        if (jobScheduler != null) {
            jobScheduler.schedule(info);
        }
    }
    public static void initializeDailyCheck(Application app) {
        JobScheduler jobScheduler = (JobScheduler) app.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName name = new ComponentName(app, AsteroidJobService.class);
        JobInfo info = new JobInfo.Builder(DAILY_ASTEROID, name)
                .setPeriodic(TimeUnit.DAYS.toMillis(1))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setPersisted(true)
                .build();
        if (jobScheduler != null) {
            jobScheduler.schedule(info);
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        switch (jobParameters.getJobId()) {
            case DANGEROUS_ASTEROID:
                DangerousController dangerousController = new DangerousController(this);
                dangerousController.start();
                return true;
            case DAILY_ASTEROID:
                Controller controller = new Controller();
                controller.start(this);
                return true;
            default:
                Log.e("Faulty error thing", "Error bad job " + jobParameters.getJobId());
                return false;
        }
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
