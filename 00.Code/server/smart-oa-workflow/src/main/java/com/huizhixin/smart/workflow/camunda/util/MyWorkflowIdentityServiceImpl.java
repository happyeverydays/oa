package com.huizhixin.smart.workflow.camunda.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.NativeUserQuery;
import org.camunda.bpm.engine.identity.PasswordPolicy;
import org.camunda.bpm.engine.identity.PasswordPolicyResult;
import org.camunda.bpm.engine.identity.PasswordPolicyRule;
import org.camunda.bpm.engine.identity.Picture;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.TenantQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.impl.ServiceImpl;
import org.camunda.bpm.engine.impl.cmd.CreateGroupCmd;
import org.camunda.bpm.engine.impl.cmd.CreateGroupQueryCmd;
import org.camunda.bpm.engine.impl.cmd.CreateMembershipCmd;
import org.camunda.bpm.engine.impl.cmd.CreateNativeUserQueryCmd;
import org.camunda.bpm.engine.impl.cmd.CreateTenantCmd;
import org.camunda.bpm.engine.impl.cmd.CreateTenantGroupMembershipCmd;
import org.camunda.bpm.engine.impl.cmd.CreateTenantQueryCmd;
import org.camunda.bpm.engine.impl.cmd.CreateTenantUserMembershipCmd;
import org.camunda.bpm.engine.impl.cmd.CreateUserCmd;
import org.camunda.bpm.engine.impl.cmd.CreateUserQueryCmd;
import org.camunda.bpm.engine.impl.cmd.DeleteGroupCmd;
import org.camunda.bpm.engine.impl.cmd.DeleteMembershipCmd;
import org.camunda.bpm.engine.impl.cmd.DeleteTenantCmd;
import org.camunda.bpm.engine.impl.cmd.DeleteTenantGroupMembershipCmd;
import org.camunda.bpm.engine.impl.cmd.DeleteTenantUserMembershipCmd;
import org.camunda.bpm.engine.impl.cmd.DeleteUserCmd;
import org.camunda.bpm.engine.impl.cmd.DeleteUserInfoCmd;
import org.camunda.bpm.engine.impl.cmd.DeleteUserPictureCmd;
import org.camunda.bpm.engine.impl.cmd.GetPasswordPolicyCmd;
import org.camunda.bpm.engine.impl.cmd.GetUserAccountCmd;
import org.camunda.bpm.engine.impl.cmd.GetUserInfoCmd;
import org.camunda.bpm.engine.impl.cmd.GetUserInfoKeysCmd;
import org.camunda.bpm.engine.impl.cmd.GetUserPictureCmd;
import org.camunda.bpm.engine.impl.cmd.IsIdentityServiceReadOnlyCmd;
import org.camunda.bpm.engine.impl.cmd.SaveGroupCmd;
import org.camunda.bpm.engine.impl.cmd.SaveTenantCmd;
import org.camunda.bpm.engine.impl.cmd.SaveUserCmd;
import org.camunda.bpm.engine.impl.cmd.SetUserInfoCmd;
import org.camunda.bpm.engine.impl.cmd.SetUserPictureCmd;
import org.camunda.bpm.engine.impl.cmd.UnlockUserCmd;
import org.camunda.bpm.engine.impl.identity.Account;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.engine.impl.identity.PasswordPolicyResultImpl;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.IdentityInfoEntity;
import org.camunda.bpm.engine.impl.util.EnsureUtil;
import org.camunda.bpm.engine.impl.util.ExceptionUtil;

/**
 * @author Tom Baeyens
 */
public class MyWorkflowIdentityServiceImpl extends ServiceImpl implements IdentityService {

  /** thread local holding the current authentication */
  private ThreadLocal<Authentication> currentAuthentication = new ThreadLocal<Authentication>();

  public boolean isReadOnly() {
    return commandExecutor.execute(new IsIdentityServiceReadOnlyCmd());
  }

  public Group newGroup(String groupId) {
    return commandExecutor.execute(new CreateGroupCmd(groupId));
  }

  public User newUser(String userId) {
    return commandExecutor.execute(new CreateUserCmd(userId));
  }

  public Tenant newTenant(String tenantId) {
    return commandExecutor.execute(new CreateTenantCmd(tenantId));
  }

  public void saveGroup(Group group) {

    try {
      commandExecutor.execute(new SaveGroupCmd((GroupEntity) group));
    } catch (ProcessEngineException ex) {
      if (ExceptionUtil.checkConstraintViolationException(ex)) {
        throw new BadUserRequestException("The group already exists", ex);
      }
      throw ex;
    }
  }

  public void saveUser(User user) {
    saveUser(user, false);
  }
  
  public void saveUser(User user, boolean skipPasswordPolicy) {
    try {
      commandExecutor.execute(new SaveUserCmd(user, skipPasswordPolicy));
    } catch (ProcessEngineException ex) {
      if (ExceptionUtil.checkConstraintViolationException(ex)) {
        throw new BadUserRequestException("The user already exists", ex);
      }
      throw ex;
    }
  }

  public void saveTenant(Tenant tenant) {

    try {
      commandExecutor.execute(new SaveTenantCmd(tenant));
    } catch (ProcessEngineException ex) {
      if (ExceptionUtil.checkConstraintViolationException(ex)) {
        throw new BadUserRequestException("The tenant already exists", ex);
      }
      throw ex;
    }
  }

  public UserQuery createUserQuery() {
    return commandExecutor.execute(new CreateUserQueryCmd());
  }

  @Override
  public NativeUserQuery createNativeUserQuery() {
    return commandExecutor.execute(new CreateNativeUserQueryCmd());
  }

  public GroupQuery createGroupQuery() {
    return commandExecutor.execute(new CreateGroupQueryCmd());
  }

  public TenantQuery createTenantQuery() {
    return commandExecutor.execute(new CreateTenantQueryCmd());
  }

  public void createMembership(String userId, String groupId) {
    commandExecutor.execute(new CreateMembershipCmd(userId, groupId));
  }

  public void deleteGroup(String groupId) {
    commandExecutor.execute(new DeleteGroupCmd(groupId));
  }

  public void deleteMembership(String userId, String groupId) {
    commandExecutor.execute(new DeleteMembershipCmd(userId, groupId));
  }

  public boolean checkPassword(String userId, String password) {
	  return true;
	  
   //  return commandExecutor.execute(new CheckPassword(userId, password));
  }

  public PasswordPolicyResult checkPasswordAgainstPolicy(String password) {
    return checkPasswordAgainstPolicy(getPasswordPolicy(), password);
  }
  
  public PasswordPolicyResult checkPasswordAgainstPolicy(PasswordPolicy policy, String password) {
    EnsureUtil.ensureNotNull("policy", policy);
    EnsureUtil.ensureNotNull("password", password);

    List<PasswordPolicyRule> violatedRules = new ArrayList<PasswordPolicyRule>();
    List<PasswordPolicyRule> fulfilledRules = new ArrayList<PasswordPolicyRule>();

    for (PasswordPolicyRule rule : policy.getRules()) {
      if (rule.execute(password)) {
        fulfilledRules.add(rule);
      } else {
        violatedRules.add(rule);
      }
    }
    return new PasswordPolicyResultImpl(violatedRules, fulfilledRules);
  }

  public PasswordPolicy getPasswordPolicy() {
    return commandExecutor.execute(new GetPasswordPolicyCmd());
  }

  public void unlockUser(String userId) {
    commandExecutor.execute(new UnlockUserCmd(userId));
  }

  public void deleteUser(String userId) {
    commandExecutor.execute(new DeleteUserCmd(userId));
  }

  public void deleteTenant(String tenantId) {
    commandExecutor.execute(new DeleteTenantCmd(tenantId));
  }

  public void setUserPicture(String userId, Picture picture) {
    commandExecutor.execute(new SetUserPictureCmd(userId, picture));
  }

  public Picture getUserPicture(String userId) {
    return commandExecutor.execute(new GetUserPictureCmd(userId));
  }

  public void deleteUserPicture(String userId) {
    commandExecutor.execute(new DeleteUserPictureCmd(userId));
  }

  public void setAuthenticatedUserId(String authenticatedUserId) {
    setAuthentication(new Authentication(authenticatedUserId, null));
  }

  public void setAuthentication(Authentication auth) {
    if(auth == null) {
      clearAuthentication();
    } else {
      if (auth.getUserId() != null) {
        EnsureUtil.ensureValidIndividualResourceId("Invalid user id provided", auth.getUserId());
      }
      if (auth.getGroupIds() != null) {
        EnsureUtil.ensureValidIndividualResourceIds("At least one invalid group id provided", auth.getGroupIds());
      }
      if (auth.getTenantIds() != null) {
        EnsureUtil.ensureValidIndividualResourceIds("At least one invalid tenant id provided", auth.getTenantIds());
      }

      currentAuthentication.set(auth);
    }
  }

  public void setAuthentication(String userId, List<String> groups) {
    setAuthentication(new Authentication(userId, groups));
  }

  public void setAuthentication(String userId, List<String> groups, List<String> tenantIds) {
    setAuthentication(new Authentication(userId, groups, tenantIds));
  }

  public void clearAuthentication() {
    currentAuthentication.remove();
  }

  public Authentication getCurrentAuthentication() {
    return currentAuthentication.get();
  }

  public String getUserInfo(String userId, String key) {
    return commandExecutor.execute(new GetUserInfoCmd(userId, key));
  }

  public List<String> getUserInfoKeys(String userId) {
    return commandExecutor.execute(new GetUserInfoKeysCmd(userId, IdentityInfoEntity.TYPE_USERINFO));
  }

  public List<String> getUserAccountNames(String userId) {
    return commandExecutor.execute(new GetUserInfoKeysCmd(userId, IdentityInfoEntity.TYPE_USERACCOUNT));
  }

  public void setUserInfo(String userId, String key, String value) {
    commandExecutor.execute(new SetUserInfoCmd(userId, key, value));
  }

  public void deleteUserInfo(String userId, String key) {
    commandExecutor.execute(new DeleteUserInfoCmd(userId, key));
  }

  public void deleteUserAccount(String userId, String accountName) {
    commandExecutor.execute(new DeleteUserInfoCmd(userId, accountName));
  }

  public Account getUserAccount(String userId, String userPassword, String accountName) {
    return commandExecutor.execute(new GetUserAccountCmd(userId, userPassword, accountName));
  }

  public void setUserAccount(String userId, String userPassword, String accountName, String accountUsername, String accountPassword, Map<String, String> accountDetails) {
    commandExecutor.execute(new SetUserInfoCmd(userId, userPassword, accountName, accountUsername, accountPassword, accountDetails));
  }

  public void createTenantUserMembership(String tenantId, String userId) {
    commandExecutor.execute(new CreateTenantUserMembershipCmd(tenantId, userId));
  }

  public void createTenantGroupMembership(String tenantId, String groupId) {
    commandExecutor.execute(new CreateTenantGroupMembershipCmd(tenantId, groupId));
  }

  public void deleteTenantUserMembership(String tenantId, String userId) {
    commandExecutor.execute(new DeleteTenantUserMembershipCmd(tenantId, userId));
  }

  public void deleteTenantGroupMembership(String tenantId, String groupId) {
    commandExecutor.execute(new DeleteTenantGroupMembershipCmd(tenantId, groupId));
  }


}
