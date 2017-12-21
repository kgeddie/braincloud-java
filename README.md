# brainCloud Java Library

Thanks for downloading the brainCloud Java client library! Here are a few notes to get you started. Further information about the brainCloud API, including example Tutorials can be found here:

http://getbraincloud.com/apidocs/

If you haven't signed up or you want to log into the brainCloud portal, you can do that here:

https://portal.braincloudservers.com/

## Troubleshooting

Here are a few common errors that you may see on your first attempt to connect to brainCloud.

- **App id not set**: Verify you've set up the app id and app secret correctly in the `initialize()` method.
- **Platform not enabled**: Verify you've enabled your platform on the portal.

If you're still having issues, log into the portal and give us a shout through the help system (bottom right icon with the question mark and chat bubble).

## brainCloud Summary

brainCloud is a ready-made back-end platform for the development of feature-rich games, apps and things. brainCloud provides the features you need – along with comprehensive tools to support your team during development, testing and user support.

brainCloud consists of:
- Cloud Service – an advanced, Software-as-a-Service (SaaS) back-end
- Client Libraries – local client libraries (SDKs)
- Design Portal – a portal that allows you to design and debug your apps
- brainCloud Architecture

![architecture](/Screenshots/bc-architecture.png?raw=true)

## What's the difference between the brainCloud Wrapper and the brainCloud Client?
The wrapper contains quality of life improvement around the brainCloud Client. It may contain device specific code, such as serializing the user's login id on an Android or iOS device.
It is recommended to use the wrapper by default.

![wrapper](/Screenshots/bc-wrapper.png?raw=true)

## How do I initialize brainCloud?
If using the wrapper use the following code.
```java
_bc = new BrainCloudWrapper(); // optionally pass in a _wrapperName
_bc.initialize(_appId, _secret, _appVersion); // optionally pass in an _applicationContext
```
On Android, to use the wrapper serialization features, you also need to pass in or set the application context.
```java
_bc.setContext(_applicationContext);
```
Your _appId, _secret, is set on the brainCloud dashboard. Under Design | Core App Info > Application IDs

![wrapper](/Screenshots/bc-ids.png?raw=true)

_wrapperName prefixes saved operations that the wrapper will make. Use a _wrapperName if you plan on having multiple instances of brainCloud running.


----------------

#### Newly upgraded?
If your app is already live, you should **NOT** specify the _wrapperName - otherwise the library will look in the wrong location for your user's stored anonymousID and profileID information. Only add a name if you intend to alter the save data.

---------------


_appVersion is the current version of our app. Having an _appVersion less than your minimum app version on brainCloud will prevent the user from accessing the service until they update their app to the lastest version you have provided them.

![wrapper](/Screenshots/bc-minVersions.png?raw=true)

## How do I keep the brainCloud SDK updating?
In your project's update loop, you're going to want to update brainCloud client so it can check for responses.

To do this, you need to call runCallbacks();

```java
_bc.runCallbacks();
```

## How do I authenticate a user with brainCloud?
The simplest form of authenticating with brainCloud Wrapper is an Anonymous Authentication.
```java
_bc.authenticateAnonymous(_callback);
```
This method will create an account, and continue to use a locally saved anonymous id.

Your _callback will inherit from IServerCallback and contain the functions needed to react to the brainCloud Server response.


To login with a specfic anonymous id, use the brainCloud client.
```java
_bc.getClient().getAuthenticationService().setAnonymousId(_anonymousId); // re-use an Anon id
_bc.getClient().getAuthenticationService().setAnonymousId(_bc.getClient().getAuthenticationService().generateAnonymousId()); // or use brainCloud to generate one
_bc.getClient().GetAuthenticationService().AuthenticateAnonymous(_forceCreate, _callback);
```
Setting _forceCreate to false will ensure the user will only login to an existing account. Setting it to true, will allow the user to register a new account

## How do I attach an email to a user's brainCloud profile?
After having the user create an anonymous with brainCloud, they are probably going to want to attach an email or username, so their account can be accessed via another platform, or when their local data is discarded.
Attaching email authenticate would look like this.
```java
_wrapper.getIdentityService().attachEmailIdentity(_email, _password, _callback);
```
There are many authentication types. You can also merge profiles and detach idenities. See the brainCloud documentation for more information:
http://getbraincloud.com/apidocs/apiref/?java#capi-auth
