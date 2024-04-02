package com.example.orientation.model.po.Admin;

import com.example.orientation.model.parent.AdminTaskParent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTaskPo extends AdminTaskParent {
    private String imageName;
}
