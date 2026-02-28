class ConstConf

  class << self
    def from(config)
      name = config['name']
      type = config['type']
      value = config['value']
      new(name, type, value)
    end
  end

  attr_reader :name, :type, :value

  def initialize(name, type, value)
    if name.nil?
      raise ArgumentError, 'name is required on const'
    else
      @name = name.to_s
    end
    if type.nil?
      raise ArgumentError, 'type is required on const'
    else
      @type = type.to_s
    end
    if value.nil?
      raise ArgumentError, 'value is required on const'
    else
      @value = value.to_s
    end
  end

end
