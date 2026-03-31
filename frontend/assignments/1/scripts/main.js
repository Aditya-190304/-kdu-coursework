document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll(".post").forEach(function (post) {
post.querySelector(".comments-count").textContent = Math.floor(Math.random() * 50);
post.querySelector(".retweets-count").textContent = Math.floor(Math.random() * 100);
    post.querySelector(".likes-count").textContent = Math.floor(Math.random() * 500);
post.querySelector(".views-count").textContent = Math.floor(Math.random() * 1000);
  });
  const posts = document.getElementById("posts");
const tweetInput = document.getElementById("tweetInput");
const postBtn = document.getElementById("postTweetBtn");
    const templatePost = posts.querySelector(".post");


    tweetInput.addEventListener("input", function () {
      postBtn.disabled = tweetInput.value.trim() === "";
  });

  postBtn.addEventListener("click", function () {
    const text = tweetInput.value.trim();

    if (!text) {
      return;
    }
        const newPost = templatePost.cloneNode(true);
        newPost.querySelector(".post-text").textContent = text;
        newPost.querySelector(".post-time").textContent = "Now";

    newPost.querySelector(".comments-count").textContent = "0";
newPost.querySelector(".retweets-count").textContent = "0";
newPost.querySelector(".likes-count").textContent = "0";
newPost.querySelector(".views-count").textContent = "0";

    const likeBtn = newPost.querySelector(".like-post");
likeBtn.classList.remove("liked");
likeBtn.classList.add("unlike-post");
    likeBtn.querySelector("img").src = "icons/like.svg";


    newPost.querySelector(".comments-section").style.display = "none";
        newPost.querySelector(".comments-list").innerHTML = "";
    newPost.querySelector(".comment-input").value = "";

    posts.prepend(newPost);

    tweetInput.value = "";
        postBtn.disabled = true;
  });

  posts.addEventListener("click", function (e) {
        const post = e.target.closest(".post");
    if (!post) {
      return;
    }
        const likeBtn = e.target.closest(".like-post");
    if (likeBtn) {
        const countEl = likeBtn.querySelector(".likes-count");
      let count = parseInt(countEl.textContent, 10);

      if (likeBtn.classList.contains("liked")) {
likeBtn.classList.remove("liked");
        likeBtn.classList.add("unlike-post");
            likeBtn.querySelector("img").src = "icons/like.svg";
        countEl.textContent = String(Math.max(0, count - 1));
      } else {
            likeBtn.classList.add("liked");
         likeBtn.classList.remove("unlike-post");
         likeBtn.querySelector("img").src = "icons/like-pink.svg";
        countEl.textContent = String(count + 1);
      }

      return;
    }
    const commentBtn = e.target.closest(".comment-post");
    if (commentBtn) {
      const section = post.querySelector(".comments-section");
         section.style.display = section.style.display === "block" ? "none" : "block";
      return;
    }

    const replyBtn = e.target.closest(".comment-submit");
    if (replyBtn) {
      const input = post.querySelector(".comment-input");
          const list = post.querySelector(".comments-list");
    const countEl = post.querySelector(".comments-count");

      const text = input.value.trim();
      if (!text) {
        return;
      }

      const comment = document.createElement("div");
      comment.className = "comment";
          comment.textContent = "@Archangel_One: " + text;

      list.appendChild(comment);
    countEl.textContent = String(parseInt(countEl.textContent, 10) + 1);

      input.value = "";
    }
  });


  posts.addEventListener("keydown", function (e) {
    if (!e.target.classList.contains("comment-input")) {
          return;
    }

    if (e.key !== "Enter") {
    return;
    }

    const post = e.target.closest(".post");
        const text = e.target.value.trim();

    if (!text) {
      return;
    }

    const list = post.querySelector(".comments-list");
       const countEl = post.querySelector(".comments-count");

    const comment = document.createElement("div");
        comment.className = "comment";
     comment.textContent = "@Archangel_One: " + text;

    list.appendChild(comment);
        countEl.textContent = String(parseInt(countEl.textContent, 10) + 1);

      e.target.value = "";
  });
});
