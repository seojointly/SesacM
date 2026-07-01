import type React from "react";
import { useParams } from "react-router-dom";

const Post: React.FC = () => {

  const { pid, cid } = useParams<{pid: string; cid: string}>();
  const postId = pid ? Number(pid) : NaN;
  const commentId = cid ? Number(cid) : NaN;

  return(
    <h3>Post 페이지 (PostId: {postId}, CommentId: {commentId})</h3>
  );
}

export default Post;