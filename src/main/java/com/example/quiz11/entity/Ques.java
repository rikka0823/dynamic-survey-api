package com.example.quiz11.entity;

import com.example.quiz11.constants.MsgConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.Constraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@IdClass(value = QuesId.class)
@Table(name = "ques")
public class Ques {

    @Id
    @Column(name = "quiz_id")
    private int quizId;

    // quesId 必須是大於等於1
    @Min(value = 1, message = MsgConstants.QUES_ID_ERROR)
    @Id
    @Column(name = "ques_id")
    private int quesId;

    @NotBlank(message = MsgConstants.QUES_NAME_ERROR)
    @Column(name = "ques_name")
    private String quesName;

    @NotBlank(message = MsgConstants.QUES_TYPE_ERROR)
    @Column(name = "type")
    private String type;

    @Column(name = "required")
    private boolean required;

    @Column(name = "options")
    private String options;

    public Ques() {
        super();
    }

    public Ques(int quizId, int quesId, String quesName, String type, boolean required, String options) {
        super();
        this.quizId = quizId;
        this.quesId = quesId;
        this.quesName = quesName;
        this.type = type;
        this.required = required;
        this.options = options;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getQuesId() {
        return quesId;
    }

    public String getQuesName() {
        return quesName;
    }

    public String getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public String getOptions() {
        return options;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public void setQuesName(String quesName) {
        this.quesName = quesName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setOptions(String options) {
        this.options = options;
    }

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
}
