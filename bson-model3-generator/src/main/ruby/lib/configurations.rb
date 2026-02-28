require_relative 'configurations/model_conf'

class Configurations

  class << self
    def from(config)
      if config.key?('java-package')
        package = config['java-package']
      else
        package = config['package']
      end
      if config.key?('models')
        models = config['models']
        unless models.is_a?(Array)
          raise ArgumentError, 'models must be an array'
        end
        models = models.map { |model| ModelConf.from(model, package) }
      else
        models = []
      end
      new(package, models)
    end
  end

  attr_reader :package, :models

  def initialize(package, models)
    if package.nil?
      raise ArgumentError, 'package is required'
    else
      package = package.to_s
    end
    @package = package
    if models.nil?
      raise ArgumentError, 'models is required'
    else
      unless models.is_a?(Array)
        raise ArgumentError, 'models must be an array'
      end
      @models = models
    end
  end

end
