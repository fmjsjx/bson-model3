require_relative 'const_conf'
require_relative 'field_conf'

class ModelConf

  class << self
    def from(config, package)
      name = config['name']
      type = config['type']
      if config.key?('consts')
        consts = config['consts']
        unless consts.is_a?(Array)
          raise ArgumentError, 'consts must be an array on model'
        end
        consts = consts.map { |const| ConstConf.from(const) }
      end
      if config.key?('fields')
        fields = config['fields']
        unless fields.is_a?(Array)
          raise ArgumentError, 'fields must be an array on model'
        end
        fields = fields.map.with_index { |field, index| FieldConf.from(field, index) }
      end
      new(package, name, type, consts, fields)
    end
  end

  attr_reader :package, :name, :type, :consts, :fields

  def initialize(package, name, type, consts, fields)
    if package.nil?
      raise ArgumentError, 'package is required on model'
    else
      @package = package.to_s
    end
    if name.nil?
      raise ArgumentError, 'name is required on model'
    else
      @name = name.to_s
    end
    if type.nil?
      raise ArgumentError, 'type is required on model'
    else
      @type = type.to_s
    end
    if consts.nil?
      @consts = []
    else
      unless consts.is_a?(Array)
        raise ArgumentError, 'consts must be an array on model'
      end
      @consts = consts
    end
    if fields.nil?
      @fields = []
    else
      unless fields.is_a?(Array)
        raise ArgumentError, 'fields must be an array on model'
      end
      @fields = fields
    end
  end

end
