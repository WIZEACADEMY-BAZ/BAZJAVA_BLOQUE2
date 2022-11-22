package com.wizeline.baz.LearningSpring.batch;

import com.wizeline.baz.LearningSpring.model.NotificacionesDTO;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

public class ItemReader {

    @Bean
    public FlatFileItemReader<NotificacionesDTO> flatFileItemReader() {
        FlatFileItemReader<NotificacionesDTO> flatFileItemReader =
                new FlatFileItemReader<NotificacionesDTO>();

        flatFileItemReader.setResource(new FileSystemResource(
                new File("src/wizelineCsv.csv")));

        flatFileItemReader.setLineMapper(new DefaultLineMapper<NotificacionesDTO>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] {"id", "pushId", "phone", "email"});
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<NotificacionesDTO>() {
                    {
                        setTargetType(NotificacionesDTO.class);
                    }
                });

            }
        });

        flatFileItemReader.setLinesToSkip(1);

        return flatFileItemReader;
    }
}
