package net.stage.Server.Controller;

import net.stage.Server.Response.ApiResponse;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/batch")
public class JobLauncherController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job exportCustomerJob;

    @GetMapping("/extract")
    public ApiResponse<String> handle() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(exportCustomerJob, jobParameters);
            return ApiResponse.<String>builder()
                    .message("Job launched successfully!")
                    .code(200)
                    .data("CSV extracted successfully!")
                    .error(null)
                    .timestamp(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .message("Job launch failed!")
                    .code(500)
                    .data(null)
                    .error(e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();
        }
    }
}
