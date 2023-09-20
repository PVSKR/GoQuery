import { Answer } from "./answer.model";


export class Question {
    strQuestionerEmail! : string;
    strQuestionTitle! : string;
    arrQuestionTags!: Array<string>;
    intQuestionUpvote!: number;
    intQuestionDownvote!: number;
    acceptedAnswer!: true;
    answers!: Array<Answer>;
}
