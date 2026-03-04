require_relative 'configurations/model_conf'

class Configurations

  class << self
    def from(config)
      if config.key?('java-package')
        package = config['java-package']
      else
        package = config['package']
      end
      if config.key?('json-libs')
        json_libs = config['json-libs']
        unless json_libs.is_a?(Array)
          raise ArgumentError, 'json-libs must be an array'
        end
        if json_libs.empty?
          json_libs = ['Jackson']
        end
      else
        json_libs = ['Jackson']
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
      new(package, json_libs, models)
    end
  end

  attr_reader :package, :json_libs, :models

  def initialize(package, json_libs, models)
    if package.nil?
      raise ArgumentError, 'package is required'
    else
      package = package.to_s
    end
    @package = package
    if json_libs.nil?
      raise ArgumentError, 'json-libs is required'
    else
      unless json_libs.is_a?(Array)
        raise ArgumentError, 'json-libs must be an array'
      end
      @json_libs = json_libs
    end
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
