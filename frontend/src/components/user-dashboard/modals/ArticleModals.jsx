import React from "react";
import Modal from "./Modal";

export default function ArticleModals(props) {
  const {
    openPlan,
    isArticleModalOpen,
    articleMode,
    setArticleMode,
    articleSearch,
    setArticleSearch,
    setSelectedArticle,
    articleOptions,
    openPlanArticles,
    getArticleId,
    articleModalFieldErrors,
    newArticle,
    setNewArticle,
    articleJournals,
    newJournalName,
    setNewJournalName,
    handleCreateJournal,
    isJournalCreating,
    journalMessage,
    articleComments,
    setArticleComments,
    articleLink,
    setArticleLink,
    handleSaveArticleFromModal,
    isArticleSaving,
    closeArticleModal,
    getButtonStyle,
    articleModalMessage,
    isArticleDeleteModalOpen,
    isArticleDeleting,
    articleDeleteMessage,
    getArticlePlanId,
    formatArticleText,
    handleDeleteArticlePlan,
    closeArticleDeleteModal,
    isArticleEditModalOpen,
    articleEditTarget,
    articleEditName,
    setArticleEditName,
    articleEditCoAuthors,
    setArticleEditCoAuthors,
    articleEditJournalId,
    setArticleEditJournalId,
    articleEditFieldErrors,
    articleEditComments,
    setArticleEditComments,
    articleEditLink,
    setArticleEditLink,
    handleEditArticlePlan,
    isArticleEditing,
    closeArticleEditModal,
    articleEditMessage,
  } = props;

  return (
    <>
      <Modal open={isArticleModalOpen}>
        <h3>Add Article To Plan #{openPlan?.idPlan}</h3>
        <div style={{ color: "green", marginTop: 4, marginBottom: 10 }}>
          After adding Article you will be able to edit only "Comment" and "Link" fields.
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>
            <input data-testid="article-use-existing-article" type="radio" name="articleMode" checked={articleMode === "existing"} onChange={() => setArticleMode("existing")} />{" "}
            Use existing article
          </label>{"  "}
          <label>
            <input data-testid="article-create-new-article" type="radio" name="articleMode" checked={articleMode === "new"} onChange={() => setArticleMode("new")} />{" "}
            Create new article
          </label>
        </div>

        {articleMode === "existing" ? (
          <div style={{ marginBottom: 10 }}>
            <label>Article autocomplete</label>
            <input
              data-testid="article-autocomplete-input"
              type="text"
              value={articleSearch}
              onChange={(e) => {
                setArticleSearch(e.target.value);
                setSelectedArticle(null);
              }}
              placeholder="Type at least 2 characters..."
              style={{ width: "100%", marginTop: 4 }}
            />
            {articleOptions.length > 0 && (
              <div style={{ border: "1px solid #ccc", maxHeight: 160, overflowY: "auto", marginTop: 4 }}>
                {articleOptions.map((article) => {
                  const journalName = article.journalName ?? article.journal ?? article.journalTitle ?? article.idJournal ?? "";
                  const idArticle = article.idArticle ?? article.idScientificArticles ?? article.idScientificArticle ?? article.articleId ?? null;
                  return (
                    <div
                      key={idArticle || article.name}
                      onClick={() => {
                        setSelectedArticle(article);
                        setArticleSearch(`${article.name} (Co-authors: ${article.coAuthors || "-"}, Journal: ${journalName || "-"})`);
                        props.setArticleOptions([]);
                      }}
                      style={{ padding: 8, cursor: "pointer", borderBottom: "1px solid #eee" }}
                    >
                      {article.name} | Co-authors: {article.coAuthors || "-"} | Journal: {journalName || "-"}
                      {(() => {
                        const existing = openPlanArticles.find((a) => Number(getArticleId(a)) === Number(idArticle));
                        return existing ? " | Already attached" : "";
                      })()}
                    </div>
                  );
                })}
              </div>
            )}
          </div>
        ) : (
          <div style={{ marginBottom: 10 }}>
            <label>Name</label>
            <input data-testid="article-name" type="text" value={newArticle.name} onChange={(e) => setNewArticle((prev) => ({ ...prev, name: e.target.value }))} style={{ width: "100%", marginTop: 4 }} />
            {articleModalFieldErrors.name && <div style={{ color: "red", marginTop: 4 }}>{articleModalFieldErrors.name}</div>}
            <div style={{ marginTop: 10 }}>
              <label>Co-authors</label>
              <input data-testid="article-coauthors" type="text" value={newArticle.coAuthors} onChange={(e) => setNewArticle((prev) => ({ ...prev, coAuthors: e.target.value }))} style={{ width: "100%", marginTop: 4 }} />
              {articleModalFieldErrors.coAuthors && <div style={{ color: "red", marginTop: 4 }}>{articleModalFieldErrors.coAuthors}</div>}
            </div>
            <div style={{ marginTop: 10 }}>
              <label>Journal</label>
              <select data-testid="article-journal-select" value={newArticle.idJournal} onChange={(e) => setNewArticle((prev) => ({ ...prev, idJournal: e.target.value }))} style={{ width: "100%", marginTop: 4 }}>
                <option value="">Select journal</option>
                {articleJournals.map((j) => (
                  <option key={j.idJournal} value={j.idJournal}>{j.name}</option>
                ))}
              </select>
              {articleModalFieldErrors.idJournal && <div style={{ color: "red", marginTop: 4 }}>{articleModalFieldErrors.idJournal}</div>}
              <div style={{ marginTop: 6 }}>
                <label>Or create new journal</label>
                <div style={{ display: "flex", gap: 8, marginTop: 4 }}>
                  <input data-testid="article-new-journal-name" type="text" value={newJournalName} onChange={(e) => setNewJournalName(e.target.value)} placeholder="New journal name" style={{ flex: 1 }} />
                  <button data-testid="article-new-journal-create" type="button" onClick={handleCreateJournal}>
                    {isJournalCreating ? "Creating..." : "Add Journal"}
                  </button>
                </div>
                {journalMessage && <div style={{ color: journalMessage.includes("created") ? "green" : "red", marginTop: 4 }}>{journalMessage}</div>}
              </div>
            </div>
          </div>
        )}

        <div style={{ marginBottom: 10 }}>
          <label>Article comments</label>
          <textarea data-testid="article-comments" rows={2} value={articleComments} onChange={(e) => setArticleComments(e.target.value)} style={{ width: "100%", marginTop: 4, resize: "vertical" }} />
          {articleModalFieldErrors.articleComments && <div style={{ color: "red", marginTop: 4 }}>{articleModalFieldErrors.articleComments}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Publication link</label>
          <input data-testid="article-publication-link" type="text" value={articleLink} onChange={(e) => setArticleLink(e.target.value)} style={{ width: "100%", marginTop: 4 }} />
          {articleModalFieldErrors.publicationLink && <div style={{ color: "red", marginTop: 4 }}>{articleModalFieldErrors.publicationLink}</div>}
        </div>

        <button data-testid="article-save-button" type="button" onClick={handleSaveArticleFromModal} disabled={isArticleSaving} style={getButtonStyle("add", isArticleSaving)}>
          {isArticleSaving ? "Saving..." : "Save Article"}
        </button>{" "}
        <button data-testid="article-cancel-button" type="button" onClick={closeArticleModal} disabled={isArticleSaving} style={getButtonStyle("cancel", isArticleSaving)}>
          Cancel
        </button>
        {articleModalMessage && <p style={{ color: "red", marginTop: 8 }}>{articleModalMessage}</p>}
      </Modal>

      <Modal open={isArticleDeleteModalOpen}>
        <h3>Delete Article From Plan #{openPlan?.idPlan}</h3>
        {openPlanArticles.length === 0 ? (
          <p>No articles attached.</p>
        ) : (
          <ol style={{ paddingLeft: 20 }}>
            {openPlanArticles.map((article, idx) => (
              <li data-testid="article-plan-list" key={`${getArticlePlanId(article) || getArticleId(article) || idx}-${idx}`} style={{ marginBottom: 8 }}>
                <div>{formatArticleText(article)}</div>
                <button data-testid={`article-delete-${idx}`} type="button" onClick={() => handleDeleteArticlePlan(article)} style={{ ...getButtonStyle("delete", isArticleDeleting), marginTop: 4 }}>
                  {isArticleDeleting ? "Deleting..." : "Delete"}
                </button>
              </li>
            ))}
          </ol>
        )}
        <button data-testid="article-close-button" type="button" onClick={closeArticleDeleteModal} disabled={isArticleDeleting}>Close</button>
        {articleDeleteMessage && <p style={{ color: "red", marginTop: 8 }}>{articleDeleteMessage}</p>}
      </Modal>

      <Modal open={isArticleEditModalOpen}>
        <h3>Edit Article Plan</h3>
        <div style={{ marginBottom: 10 }}><div>{articleEditTarget ? formatArticleText(articleEditTarget) : ""}</div></div>
        <div style={{ marginBottom: 10 }}>
          <label>Name</label>
          <input data-testid="article-edit-name" type="text" value={articleEditName} onChange={(e) => setArticleEditName(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {articleEditFieldErrors.name && <div style={{ color: "red", marginTop: 4 }}>{articleEditFieldErrors.name}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Co-authors</label>
          <input data-testid="article-edit-coauthors" type="text" value={articleEditCoAuthors} onChange={(e) => setArticleEditCoAuthors(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }} />
          {articleEditFieldErrors.coAuthors && <div style={{ color: "red", marginTop: 4 }}>{articleEditFieldErrors.coAuthors}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Journal</label>
          <select data-testid="article-all-journals" value={articleEditJournalId} onChange={(e) => setArticleEditJournalId(e.target.value)} disabled style={{ width: "100%", marginTop: 4 }}>
            <option value="">Select journal</option>
            {articleJournals.map((j) => (
              <option key={j.idJournal} value={j.idJournal}>{j.name}</option>
            ))}
          </select>
          <div style={{ marginTop: 6 }}>
            <label>Or create new journal</label>
            <div style={{ display: "flex", gap: 8, marginTop: 4 }}>
              <input type="text" value={newJournalName} onChange={(e) => setNewJournalName(e.target.value)} placeholder="New journal name" disabled style={{ flex: 1 }} />
              <button type="button" onClick={handleCreateJournal} disabled>
                {isJournalCreating ? "Creating..." : "Add Journal"}
              </button>
            </div>
            {journalMessage && <div style={{ color: journalMessage.includes("created") ? "green" : "red", marginTop: 4 }}>{journalMessage}</div>}
          </div>
          {articleEditFieldErrors.idJournal && <div style={{ color: "red", marginTop: 4 }}>{articleEditFieldErrors.idJournal}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Article comments</label>
          <textarea data-testid="article-edit-comments" rows={2} value={articleEditComments} onChange={(e) => setArticleEditComments(e.target.value)} style={{ width: "100%", marginTop: 4, resize: "vertical" }} />
          {articleEditFieldErrors.articleComments && <div style={{ color: "red", marginTop: 4 }}>{articleEditFieldErrors.articleComments}</div>}
        </div>
        <div style={{ marginBottom: 10 }}>
          <label>Publication link</label>
          <input data-testid="article-edit-publication-link" type="text" value={articleEditLink} onChange={(e) => setArticleEditLink(e.target.value)} style={{ width: "100%", marginTop: 4 }} />
          {articleEditFieldErrors.publicationLink && <div style={{ color: "red", marginTop: 4 }}>{articleEditFieldErrors.publicationLink}</div>}
        </div>
        <button data-testid="article-edit-save-button" type="button" onClick={handleEditArticlePlan} disabled={isArticleEditing} style={getButtonStyle("update", isArticleEditing)}>
          {isArticleEditing ? "Saving..." : "Save"}
        </button>{" "}
        <button data-testid="article-edit-cancel-button" type="button" onClick={closeArticleEditModal} disabled={isArticleEditing} style={getButtonStyle("cancel", isArticleEditing)}>
          Cancel
        </button>
        {articleEditMessage && <p style={{ color: "red", marginTop: 8 }}>{articleEditMessage}</p>}
      </Modal>
    </>
  );
}
