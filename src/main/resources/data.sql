
INSERT INTO `UserGroup` (Name)
VALUES ('NotApproval')
;

INSERT INTO `UserGroup` (Name)
VALUES ('Member')
;

INSERT INTO `UserGroup` (Name)
VALUES ('Admin')
;

INSERT INTO `UserGroupAuth` (UserGroupId, Auth)
SELECT id, 0
FROM `UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `UserGroupAuth` (UserGroupId, Auth)
SELECT id, 1
FROM `UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `UserGroupAuth` (UserGroupId, Auth)
SELECT id, 2
FROM `UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `UserGroupAuth` (UserGroupId, Auth)
SELECT id, 0
FROM `UserGroup`
WHERE Name = 'Member'
;

INSERT INTO `UserGroupAuth` (UserGroupId, Auth)
SELECT id, 1
FROM `UserGroup`
WHERE Name = 'Member'
;

INSERT INTO `UserGroupAuth` (UserGroupId, Auth)
SELECT id, 0
FROM `UserGroup`
WHERE Name = 'NotApproval'
;

INSERT INTO `User` (AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled, Password, RegisterDate, Username, UserGroupId)
SELECT 1, 1, 1, 1, '$2a$12$/kwx9QzXMuR0WU.H88uq5OM0d91GMhnimG7WDPXfHFz9Yt7kUgSym', now(), 'selabdev', id
FROM `UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `User` (AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled, Password, RegisterDate, Username, UserGroupId)
SELECT 1, 1, 1, 1, '$2a$12$/kwx9QzXMuR0WU.H88uq5OM0d91GMhnimG7WDPXfHFz9Yt7kUgSym', now(), 'selebadmin', id
FROM `UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `User` (AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled, Password, RegisterDate, Username, UserGroupId)
SELECT 1, 1, 1, 1, '$2a$12$/kwx9QzXMuR0WU.H88uq5OM0d91GMhnimG7WDPXfHFz9Yt7kUgSym', now(), 'selebmember', id
FROM `UserGroup`
WHERE Name = 'Member'
;

INSERT INTO `User` (AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled, Password, RegisterDate, Username, UserGroupId)
SELECT 1, 1, 1, 1, '$2a$12$/kwx9QzXMuR0WU.H88uq5OM0d91GMhnimG7WDPXfHFz9Yt7kUgSym', now(), 'selebregister', id
FROM `UserGroup`
WHERE Name = 'NotApproval'
;