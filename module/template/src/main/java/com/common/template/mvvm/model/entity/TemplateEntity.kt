package com.common.template.mvvm.model.entity
import com.common.res.http.net.BaseResponse
import com.squareup.moshi.Json



data class TemplateEntity(
    @field:Json(name = "incomplete_results") val incompleteResults: Boolean = false,
    @field:Json(name = "items") val items: List<Item> = listOf(),
    @field:Json(name = "total_count") val totalCount: Int = 0
):BaseResponse<TemplateEntity>() {
    data class Item(
        @field:Json(name = "allow_forking") val allowForking: Boolean = false,
        @field:Json(name = "archive_url") val archiveUrl: String = "",
        @field:Json(name = "archived") val archived: Boolean = false,
        @field:Json(name = "assignees_url") val assigneesUrl: String = "",
        @field:Json(name = "blobs_url") val blobsUrl: String = "",
        @field:Json(name = "branches_url") val branchesUrl: String = "",
        @field:Json(name = "clone_url") val cloneUrl: String = "",
        @field:Json(name = "collaborators_url") val collaboratorsUrl: String = "",
        @field:Json(name = "comments_url") val commentsUrl: String = "",
        @field:Json(name = "commits_url") val commitsUrl: String = "",
        @field:Json(name = "compare_url") val compareUrl: String = "",
        @field:Json(name = "contents_url") val contentsUrl: String = "",
        @field:Json(name = "contributors_url") val contributorsUrl: String = "",
        @field:Json(name = "created_at") val createdAt: String = "",
        @field:Json(name = "default_branch") val defaultBranch: String = "",
        @field:Json(name = "deployments_url") val deploymentsUrl: String = "",
        @field:Json(name = "description") val description: String = "",
        @field:Json(name = "disabled") val disabled: Boolean = false,
        @field:Json(name = "downloads_url") val downloadsUrl: String = "",
        @field:Json(name = "events_url") val eventsUrl: String = "",
        @field:Json(name = "fork") val fork: Boolean = false,
        @field:Json(name = "forks") val forks: Int = 0,
        @field:Json(name = "forks_count") val forksCount: Int = 0,
        @field:Json(name = "forks_url") val forksUrl: String = "",
        @field:Json(name = "full_name") val fullName: String = "",
        @field:Json(name = "git_commits_url") val gitCommitsUrl: String = "",
        @field:Json(name = "git_refs_url") val gitRefsUrl: String = "",
        @field:Json(name = "git_tags_url") val gitTagsUrl: String = "",
        @field:Json(name = "git_url") val gitUrl: String = "",
        @field:Json(name = "has_discussions") val hasDiscussions: Boolean = false,
        @field:Json(name = "has_downloads") val hasDownloads: Boolean = false,
        @field:Json(name = "has_issues") val hasIssues: Boolean = false,
        @field:Json(name = "has_pages") val hasPages: Boolean = false,
        @field:Json(name = "has_projects") val hasProjects: Boolean = false,
        @field:Json(name = "has_wiki") val hasWiki: Boolean = false,
        @field:Json(name = "homepage") val homepage: String = "",
        @field:Json(name = "hooks_url") val hooksUrl: String = "",
        @field:Json(name = "html_url") val htmlUrl: String = "",
        @field:Json(name = "id") val id: Int = 0,
        @field:Json(name = "is_template") val isTemplate: Boolean = false,
        @field:Json(name = "issue_comment_url") val issueCommentUrl: String = "",
        @field:Json(name = "issue_events_url") val issueEventsUrl: String = "",
        @field:Json(name = "issues_url") val issuesUrl: String = "",
        @field:Json(name = "keys_url") val keysUrl: String = "",
        @field:Json(name = "labels_url") val labelsUrl: String = "",
        @field:Json(name = "language") val language: String? = "",
        @field:Json(name = "languages_url") val languagesUrl: String = "",
        @field:Json(name = "license") val license: License? = License(),
        @field:Json(name = "merges_url") val mergesUrl: String = "",
        @field:Json(name = "milestones_url") val milestonesUrl: String = "",
        @field:Json(name = "mirror_url") val mirrorUrl: Any? = Any(),
        @field:Json(name = "name") val name: String = "",
        @field:Json(name = "node_id") val nodeId: String = "",
        @field:Json(name = "notifications_url") val notificationsUrl: String = "",
        @field:Json(name = "open_issues") val openIssues: Int = 0,
        @field:Json(name = "open_issues_count") val openIssuesCount: Int = 0,
        @field:Json(name = "owner") val owner: Owner = Owner(),
        @field:Json(name = "private") val `private`: Boolean = false,
        @field:Json(name = "pulls_url") val pullsUrl: String = "",
        @field:Json(name = "pushed_at") val pushedAt: String = "",
        @field:Json(name = "releases_url") val releasesUrl: String = "",
        @field:Json(name = "score") val score: Int = 0,
        @field:Json(name = "size") val size: Int = 0,
        @field:Json(name = "ssh_url") val sshUrl: String = "",
        @field:Json(name = "stargazers_count") val stargazersCount: Int = 0,
        @field:Json(name = "stargazers_url") val stargazersUrl: String = "",
        @field:Json(name = "statuses_url") val statusesUrl: String = "",
        @field:Json(name = "subscribers_url") val subscribersUrl: String = "",
        @field:Json(name = "subscription_url") val subscriptionUrl: String = "",
        @field:Json(name = "svn_url") val svnUrl: String = "",
        @field:Json(name = "tags_url") val tagsUrl: String = "",
        @field:Json(name = "teams_url") val teamsUrl: String = "",
        @field:Json(name = "topics") val topics: List<String> = listOf(),
        @field:Json(name = "trees_url") val treesUrl: String = "",
        @field:Json(name = "updated_at") val updatedAt: String = "",
        @field:Json(name = "url") val url: String = "",
        @field:Json(name = "visibility") val visibility: String = "",
        @field:Json(name = "watchers") val watchers: Int = 0,
        @field:Json(name = "watchers_count") val watchersCount: Int = 0,
        @field:Json(name = "web_commit_signoff_required") val webCommitSignoffRequired: Boolean = false
    ) {
        data class License(
            @field:Json(name = "key") val key: String = "",
            @field:Json(name = "name") val name: String = "",
            @field:Json(name = "node_id") val nodeId: String = "",
            @field:Json(name = "spdx_id") val spdxId: String = "",
            @field:Json(name = "url") val url: String = ""
        )

        data class Owner(
            @field:Json(name = "avatar_url") val avatarUrl: String = "",
            @field:Json(name = "events_url") val eventsUrl: String = "",
            @field:Json(name = "followers_url") val followersUrl: String = "",
            @field:Json(name = "following_url") val followingUrl: String = "",
            @field:Json(name = "gists_url") val gistsUrl: String = "",
            @field:Json(name = "gravatar_id") val gravatarId: String = "",
            @field:Json(name = "html_url") val htmlUrl: String = "",
            @field:Json(name = "id") val id: Int = 0,
            @field:Json(name = "login") val login: String = "",
            @field:Json(name = "node_id") val nodeId: String = "",
            @field:Json(name = "organizations_url") val organizationsUrl: String = "",
            @field:Json(name = "received_events_url") val receivedEventsUrl: String = "",
            @field:Json(name = "repos_url") val reposUrl: String = "",
            @field:Json(name = "site_admin") val siteAdmin: Boolean = false,
            @field:Json(name = "starred_url") val starredUrl: String = "",
            @field:Json(name = "subscriptions_url") val subscriptionsUrl: String = "",
            @field:Json(name = "type") val type: String = "",
            @field:Json(name = "url") val url: String = ""
        )
    }

    override val code: Int
        get() = 200
    override val msg: String
        get() = ""
}