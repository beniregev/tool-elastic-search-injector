/* Load the latest version of our Shared Library defined here:
 *  http://tlvgit02:7990/scm/v10/tool-elastic-search-injector.git
 */

@Library('SharedLibraries')
import ccih.jenkins.*

String serviceName = 'tool-elastic-search-injector'
String gitBranch = 'master'
String sortServiceName = 'Mock'
String groupType = 'skipDeploy'
String agentLabel = 'linux'
String projectType = 'MVN'

def builder = new JenkinsPipelineBootstrap().createBuilder(projectType)
builder.mavenApplicationPipeline(agentLabel,serviceName, gitBranch, sortServiceName, groupType)