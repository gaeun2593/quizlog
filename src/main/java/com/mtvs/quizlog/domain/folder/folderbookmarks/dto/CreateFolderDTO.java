package com.mtvs.quizlog.domain.folder.folderbookmarks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFolderDTO {

    private long folderChapterId;
    private long chapterId;
}
