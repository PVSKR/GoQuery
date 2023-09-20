import { Comment } from "./comment.model";

export class Answer {
    strAnswererEmail:string;
    strAnswerStatement:string;
    intAnswerUpvote:number;
    intAnswerDownvote:number;
    accepted:boolean;
    comment:Array<Comment>;
     constructor(){}
}
